package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.ProductInventoryInputDto;
import br.com.devpaulosouza.easybuy.dto.ProductInventoryOutputDto;
import br.com.devpaulosouza.easybuy.service.AuthService;
import br.com.devpaulosouza.easybuy.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/product/inventory")
@CrossOrigin
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService service;

    @Autowired
    private AuthService authService;


    @PutMapping
    public Mono<ResponseEntity<ProductInventoryOutputDto>> updateInventory(
            @Valid @RequestBody ProductInventoryInputDto productDto,
            @CookieValue(value = "gambi_web_token", required = false) UUID token
    ) {
        return service
                .updateInventory(productDto, token)
                .map(ResponseEntity::ok);
    }

}
