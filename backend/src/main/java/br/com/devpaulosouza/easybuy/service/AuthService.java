package br.com.devpaulosouza.easybuy.service;

import br.com.devpaulosouza.easybuy.dto.UserInputDto;
import br.com.devpaulosouza.easybuy.dto.UserLoginDto;
import br.com.devpaulosouza.easybuy.dto.UserOutputDto;
import br.com.devpaulosouza.easybuy.enumeration.AuthorityType;
import br.com.devpaulosouza.easybuy.mapper.UserMapper;
import br.com.devpaulosouza.easybuy.model.User;
import br.com.devpaulosouza.easybuy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A segurança mais segura do mundo. Nem usa essa classe aqui direito
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserRepository userRepository;

    public Mono<UserOutputDto> createUser(UserInputDto inputDto) {
        return Mono.zip(
                values -> this.createUserAggregate(inputDto, (Boolean) values[0]),
                Mono.fromCallable(() -> userRepository.existsByUsername(inputDto.getUsername()))
        )
                .flatMap((user) -> Mono.fromCallable(() -> userRepository.save(user)))
                .map(mapper::toDto)
                .map(userOutputDto -> {
                    log.debug(userOutputDto.toString());
                    return userOutputDto;
                })
                .log();
    }

    public Mono<UserOutputDto> authenticate(UserLoginDto inputDto) {
        return Mono.just(inputDto)
                .flatMap((userInputDto) -> Mono.fromCallable(() -> userRepository.findByUsername(userInputDto.getUsername())))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "E-mail or password is invalid")))
                .map(user -> {
                    if (!inputDto.getPassword().equals(user.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "E-mail or password is invalid");
                    }
                    user.setToken(UUID.randomUUID());
                    user.setTokenValidUntil(LocalDateTime.now().plusDays(7));
                    return user;
                })
                .flatMap((user -> Mono.fromCallable(() -> userRepository.save(user))))
                .map(mapper::toDto);
    }

    private User createUserAggregate(UserInputDto userInputDto, Boolean alreadyExists) {
        if (alreadyExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail already registered");
        }

        if (!this.isValidEmailAddress(userInputDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "E-mail is invalid");
        }

        userInputDto.setUsername(userInputDto.getUsername().toLowerCase());

        User user = mapper.toEntity(userInputDto);
        user.setRole(AuthorityType.NOT_ADMIN);

        return user;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public Mono<Void> hasPermission(UUID token, AuthorityType role) {

        if (Objects.isNull(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        return Mono.just(token)
                .flatMap(tkn -> Mono.fromCallable(() -> userRepository.findByToken(tkn)))
                .map(user -> {
                    if (AuthorityType.ADMIN.equals(role) && !AuthorityType.ADMIN.equals(user.getRole())) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
                    }
                    if (LocalDateTime.now().isAfter(user.getTokenValidUntil())) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token expired");
                    }
                    return user;
                })
                .then();
    }
}
