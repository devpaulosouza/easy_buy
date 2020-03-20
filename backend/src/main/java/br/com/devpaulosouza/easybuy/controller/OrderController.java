package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.OrderInputDto;
import br.com.devpaulosouza.easybuy.dto.OrderOutputDto;
import br.com.devpaulosouza.easybuy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public Mono<ResponseEntity<OrderOutputDto>> create(@Valid @RequestBody OrderInputDto orderInputDto) {
        return service
                .create(orderInputDto)
                .map(ResponseEntity::ok);
    }
//
//    @GetMapping
//    public Mono<ResponseEntity<Page<ProductSimplifiedDto>>> findAll(Pageable pageable) {
//        return service
//                .findAll(pageable)
//                .map(ResponseEntity::ok);
//    }
//
//    @GetMapping("/{uuidProduct}")
//    public Mono<ResponseEntity<ProductDto>> findByUuid(@PathVariable("uuidProduct") UUID uuidProduct) {
//        return service
//                .findByUuid(uuidProduct)
//                .map(ResponseEntity::ok);
//    }

}
