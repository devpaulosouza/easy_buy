package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.ProductDto;
import br.com.devpaulosouza.easybuy.dto.ProductSimplifiedDto;
import br.com.devpaulosouza.easybuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.InvalidObjectException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping
    public Mono<ResponseEntity<ProductDto>> create(@Valid @RequestBody ProductDto productDto) {
        return service
                .create(productDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<Page<ProductSimplifiedDto>>> findAll(Pageable pageable) {
        return service
                .findAll(pageable)
                .map(ResponseEntity::ok);
    }

}
