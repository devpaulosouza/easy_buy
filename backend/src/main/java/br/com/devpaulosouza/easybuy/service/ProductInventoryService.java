package br.com.devpaulosouza.easybuy.service;

import br.com.devpaulosouza.easybuy.dto.ProductInventoryInputDto;
import br.com.devpaulosouza.easybuy.dto.ProductInventoryOutputDto;
import br.com.devpaulosouza.easybuy.enumeration.AuthorityType;
import br.com.devpaulosouza.easybuy.mapper.ProductInventoryMapper;
import br.com.devpaulosouza.easybuy.model.Product;
import br.com.devpaulosouza.easybuy.model.ProductInventory;
import br.com.devpaulosouza.easybuy.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryMapper mapper;

    @Autowired
    private ProductInventoryRepository repository;

    @Autowired
    private AuthService authService;

    public Mono<ProductInventoryOutputDto> updateInventory(ProductInventoryInputDto productDto, UUID token) {
        return authService.checkUserToken(token, AuthorityType.ADMIN)
                .then(
                        Mono.zip(
                                values -> createProductInventoryAggregate((ProductInventory) values[0], productDto),
                                Mono.fromCallable(() -> repository.findLastProductInventoryByUuidProduct(productDto.getProductId()))
                        )
                                .flatMap((productInventory) -> Mono.fromCallable(() -> repository.save(productInventory)))
                                .map(mapper::toDto)
                );
    }

    public Mono<Void> updateInventory(Product product, BigDecimal quantityOffset) {
        ProductInventoryInputDto productDto = new ProductInventoryInputDto();
        productDto.setProductId(product.getUuid());
        productDto.setQuantityOffset(quantityOffset);

        return Mono.zip(
                values -> createProductInventoryAggregate((ProductInventory) values[0], productDto),
                Mono.fromCallable(() -> repository.findLastProductInventoryByUuidProduct(productDto.getProductId()))
        )
                .flatMap((productInventory) -> Mono.fromCallable(() -> repository.save(productInventory)))
                .then();
    }

    public Mono<Void> create(Product product) {
        return Mono.just(product)
                .map(this::mapProductToProductInventory)
                .flatMap((productInventory) -> Mono.fromCallable(() -> repository.save(productInventory)))
                .then();
    }

    private ProductInventory createProductInventoryAggregate(
            ProductInventory productInventory,
            ProductInventoryInputDto productDto
    ) {
        ProductInventory newProductInventory = new ProductInventory();
        newProductInventory.setQuantity(productInventory.getQuantity().add(productDto.getQuantityOffset()));
        newProductInventory.setProduct(productInventory.getProduct());
        newProductInventory.setPreviousQuantity(productInventory.getQuantity());
        newProductInventory.setQuantityOffset(productDto.getQuantityOffset());

        return newProductInventory;
    }


    private ProductInventory mapProductToProductInventory(Product product) {
        ProductInventory inventory = new ProductInventory();

        inventory.setQuantityOffset(product.getQuantity());
        inventory.setPreviousQuantity(BigDecimal.ZERO);
        inventory.setQuantity(product.getQuantity());
        inventory.setProduct(product);

        return inventory;
    }

}
