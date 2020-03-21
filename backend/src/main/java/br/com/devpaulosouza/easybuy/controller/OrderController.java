package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.OrderDetailedDto;
import br.com.devpaulosouza.easybuy.dto.OrderInputDto;
import br.com.devpaulosouza.easybuy.dto.OrderSimplifiedDto;
import br.com.devpaulosouza.easybuy.service.AuthService;
import br.com.devpaulosouza.easybuy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private AuthService authService;

    @PostMapping
    public Mono<ResponseEntity<OrderDetailedDto>> create(
            @Valid @RequestBody OrderInputDto orderInputDto,
            @CookieValue(value = "gambi_web_token", required = false) UUID token
    ) {
        return service
                .create(orderInputDto, token)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<Page<OrderSimplifiedDto>>> findAll(
            @RequestParam(value = "userId", required = false) UUID userId,
            @CookieValue(value = "gambi_web_token", required = false) UUID token,
            Pageable pageable
    ) {
        return service
                .findAll(pageable, userId, token)
                .map(ResponseEntity::ok);
    }


    @GetMapping("/{orderId}")
    public Mono<ResponseEntity<OrderDetailedDto>> findByUuid(@PathVariable("orderId") UUID uuidProduct) {
        return service
                .findByUuid(uuidProduct)
                .map(ResponseEntity::ok);
    }

}
