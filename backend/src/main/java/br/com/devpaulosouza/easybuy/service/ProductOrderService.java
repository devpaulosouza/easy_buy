package br.com.devpaulosouza.easybuy.service;

import br.com.devpaulosouza.easybuy.model.ProductOrder;
import br.com.devpaulosouza.easybuy.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class ProductOrderService {

    @Autowired
    ProductOrderRepository repository;

    @Autowired
    ProductInventoryService productInventoryService;

    public Mono<List<ProductOrder>> create(List<ProductOrder> productOrder) {
        return Flux.just(productOrder)
                .flatMap(productOrders -> Flux.fromIterable(repository.saveAll(productOrders)))
                .flatMap(productOrders -> Flux.just(productOrders)
                        .flatMap(po -> productInventoryService.updateInventory(po.getProduct(), po.getQuantity().negate()))
                        .map((po) -> productOrders)
                )
                .collectList();
    }

}
