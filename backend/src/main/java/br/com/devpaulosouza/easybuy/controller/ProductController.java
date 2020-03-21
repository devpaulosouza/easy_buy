package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.ProductDto;
import br.com.devpaulosouza.easybuy.dto.ProductSimplifiedDto;
import br.com.devpaulosouza.easybuy.enumeration.AuthorityType;
import br.com.devpaulosouza.easybuy.service.AuthService;
import br.com.devpaulosouza.easybuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    AuthService authService;

    @PostMapping
    public Mono<ResponseEntity<ProductDto>> create(
            @Valid @RequestBody ProductDto productDto,
            @CookieValue(value = "gambi_web_token", required = false) UUID token
    ) {
        return authService
                .checkUserToken(token, AuthorityType.ADMIN)
                .then(service.create(productDto))
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<Page<ProductSimplifiedDto>>> findAll(Pageable pageable) {
        return service
                .findAll(pageable)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{uuidProduct}")
    public Mono<ResponseEntity<ProductDto>> findByUuid(@PathVariable("uuidProduct") UUID uuidProduct) {
        return service
                .findByUuid(uuidProduct)
                .map(ResponseEntity::ok);
    }

}
