package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.ProductInventoryInputDto;
import br.com.devpaulosouza.easybuy.dto.ProductInventoryOutputDto;
import br.com.devpaulosouza.easybuy.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/product/inventory")
public class ProductInventoryController {

    @Autowired
    ProductInventoryService service;

    @PutMapping
    public Mono<ResponseEntity<ProductInventoryOutputDto>> updateInventory(@Valid @RequestBody ProductInventoryInputDto productDto) {
        return service
                .updateInventory(productDto)
                .map(ResponseEntity::ok);
    }

}
