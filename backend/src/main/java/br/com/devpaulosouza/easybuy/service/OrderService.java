package br.com.devpaulosouza.easybuy.service;

import br.com.devpaulosouza.easybuy.dto.*;
import br.com.devpaulosouza.easybuy.mapper.OrderMapper;
import br.com.devpaulosouza.easybuy.model.Order;
import br.com.devpaulosouza.easybuy.model.Product;
import br.com.devpaulosouza.easybuy.model.ProductOrder;
import br.com.devpaulosouza.easybuy.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductInventoryService productInventoryService;

    public Mono<OrderOutputDto> create(OrderInputDto orderInputDto) {

        return Mono.zip(
                values -> createOrderAggregate(orderInputDto, (List<Product>) values[0], (Long) values[1]),
                productService.findAllByUuid(
                        orderInputDto.getProducts()
                                .stream()
                                .map(ProductOrderInputDto::getProductId)
                                .collect(Collectors.toList())
                ).collectList(),
                this.getNextNumber()
        )
                .flatMap((productInventory) -> Mono.fromCallable(()-> orderRepository.save(productInventory)))
                .flatMap(order -> Mono.just(order)
                        .flatMap((o) -> productOrderService.create(o.getProducts()))
                        .map((p) -> order))
                .map(mapper::toDto);
    }

    public Mono<OrderDetailedDto> findByUuid(UUID uuidProduct) {
        return Mono.just(uuidProduct)
                .flatMap(uuid -> Mono.fromCallable(() -> orderRepository.findByUuid(uuid)))
                .map(mapper::toDetailedDto);
    }

    public Mono<Page<OrderSimplifiedDto>> findAll(Pageable pageable) {
        return Mono.just(pageable)
                .flatMap(pageRequest -> Mono.fromCallable(() -> orderRepository.findAll(pageRequest)))
                .map(orders -> orders.map(mapper::toSimplifiedDto));
    }

    public Mono<Long> getNextNumber() {
        return Mono.fromCallable(() -> orderRepository.getNextNumber());
    }

    private Order createOrderAggregate(OrderInputDto orderInputDto, List<Product> value, Long nextNumber) {

        List<UUID> productsPersisted = value
                .stream()
                .map(Product::getUuid)
                .collect(Collectors.toList());

        List<UUID> productsInOrder = orderInputDto
                .getProducts()
                .stream()
                .map(ProductOrderInputDto::getProductId)
                .collect(Collectors.toList());

        productsInOrder.removeAll(productsPersisted);

        if (!productsInOrder.isEmpty()) {
            String uuids = productsInOrder.stream().map(UUID::toString).collect(Collectors.joining(", "));

            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The product(s) " + uuids + " does not exist");
        }

        Order order = mapper.toEntity(orderInputDto);

        order.getProducts().forEach(productOrder -> {
            Optional<Product> first = value.stream()
                    .filter(product -> product.getUuid().equals(productOrder.getProduct().getUuid())).findFirst();
            first.ifPresent(productOrder::setProduct);

            productOrder.setOrder(order);
        });

        order.getProducts().forEach(productOrder -> {
            if (productOrder.getQuantity().compareTo(productOrder.getProduct().getQuantity()) > 0) {
                throw new ResponseStatusException(
                        HttpStatus.INSUFFICIENT_STORAGE,
                        "Insufficient storage for product " + productOrder.getProduct().getDescription()
                );
            }
        });

        List<ProductOrder> productsWithPrices = order.getProducts()
                .stream()
                .peek(productOrder -> productOrder.setPrice(productOrder.getQuantity().multiply(productOrder.getProduct().getPrice())))
                .collect(Collectors.toList());

        order.setProducts(productsWithPrices);

        order.setNumber(nextNumber);

        return order;
    }

}
