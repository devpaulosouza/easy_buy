package br.com.devpaulosouza.easybuy.controller;

import br.com.devpaulosouza.easybuy.dto.UserInputDto;
import br.com.devpaulosouza.easybuy.dto.UserLoginDto;
import br.com.devpaulosouza.easybuy.dto.UserOutputDto;
import br.com.devpaulosouza.easybuy.dto.UserOutputSimplifiedDto;
import br.com.devpaulosouza.easybuy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Esse controller aqui é só encheção de linguiça, ngm usa Basic auth pra um site de venda
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/user")
    Mono<ResponseEntity<UserOutputDto>> createUser(
            @RequestBody @Valid UserInputDto userInputDto,
            HttpServletResponse response
    ) {
        return service.createUser(userInputDto)
                .map(userOutputDto -> createAuthResponse(response, userOutputDto));
    }

    @GetMapping("/user")
    Mono<ResponseEntity<Page<UserOutputSimplifiedDto>>> findAll(
            @RequestHeader(value = "gambi_web_token", required = false) UUID token,
            Pageable pageable
    ) {
        return service
                .findAll(token, pageable)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    Mono<ResponseEntity<UserOutputDto>> authenticate(
            @RequestBody @Valid UserLoginDto userInputDto,
            HttpServletResponse response
    ) {
        return service.authenticate(userInputDto)
                .map((outputDto) -> createAuthResponse(response, outputDto));
    }

    private ResponseEntity<UserOutputDto> createAuthResponse(HttpServletResponse response, UserOutputDto outputDto) {
        Cookie cookie = new Cookie("gambi_web_token", outputDto.getToken().toString());

        cookie.setMaxAge(24 * 60 * 60);

        cookie.setSecure(false);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok(outputDto);
    }

}
