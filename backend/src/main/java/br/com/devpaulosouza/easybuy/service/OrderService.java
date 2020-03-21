package br.com.devpaulosouza.easybuy.service;

import br.com.devpaulosouza.easybuy.dto.*;
import br.com.devpaulosouza.easybuy.enumeration.AuthorityType;
import br.com.devpaulosouza.easybuy.mapper.OrderMapper;
import br.com.devpaulosouza.easybuy.model.Order;
import br.com.devpaulosouza.easybuy.model.Product;
import br.com.devpaulosouza.easybuy.model.ProductOrder;
import br.com.devpaulosouza.easybuy.model.User;
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
import java.util.Objects;
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

    @Autowired
    private AuthService authService;

    public Mono<OrderDetailedDto> create(OrderInputDto orderInputDto, UUID token) {

        return Mono.zip(
                values -> createOrderAggregate(orderInputDto, (List<Product>) values[0], (Long) values[1], (User) values[2]),
                productService.findAllByUuid(
                        orderInputDto.getProducts()
                                .stream()
                                .map(ProductOrderInputDto::getProductId)
                                .collect(Collectors.toList())
                ).collectList(),
                this.getNextNumber(),
                authService.checkUserToken(token, AuthorityType.NOT_ADMIN)
        )
                .flatMap((productInventory) -> Mono.fromCallable(()-> orderRepository.save(productInventory)))
                .flatMap(order -> Mono.just(order)
                        .flatMap((o) -> productOrderService.create(o.getProducts()))
                        .map((p) -> order))
                .map(mapper::toDetailedDto);
    }

    public Mono<OrderDetailedDto> findByUuid(UUID productId, UUID token) {
        return authService.checkUserToken(token, AuthorityType.NOT_ADMIN)
                .flatMap(user -> Mono.fromCallable(() -> orderRepository.findByUuidAndUserId(productId, user.getId())))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")))
                .map(mapper::toDetailedDto);
    }

    public Mono<Page<OrderSimplifiedDto>> findAll(Pageable pageable, UUID userId, UUID token) {
        return authService
                .checkUserToken(token, AuthorityType.NOT_ADMIN)
                .flatMap(user -> {

                    // apenas ADMIN podem buscar todos Orders
                    if (AuthorityType.ADMIN.equals(user.getRole())) {
                        if (Objects.isNull(userId)) {
                            return Mono.fromCallable(() -> orderRepository.findAll(pageable));
                        } else {
                            return Mono.fromCallable(() -> orderRepository.findAllByUserUuid(userId, pageable));
                        }
                    } else {
                        return Mono.fromCallable(() -> orderRepository.findAllByUserUuid(user.getUuid(), pageable));
                    }

                })
                .map(orders -> orders.map(mapper::toSimplifiedDto));
    }

    public Mono<Long> getNextNumber() {
        return Mono.fromCallable(() -> orderRepository.getNextNumber());
    }

    private Order createOrderAggregate(OrderInputDto orderInputDto, List<Product> value, Long nextNumber, User user) {

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

        order.setUser(user);

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
