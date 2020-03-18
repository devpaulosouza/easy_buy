package br.com.devpaulosouza.easybuy.service;

import br.com.devpaulosouza.easybuy.dto.ProductDto;
import br.com.devpaulosouza.easybuy.dto.ProductSimplifiedDto;
import br.com.devpaulosouza.easybuy.mapper.ProductMapper;
import br.com.devpaulosouza.easybuy.model.Product;
import br.com.devpaulosouza.easybuy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductMapper mapper;

    @Autowired
    ProductRepository repository;

    public Mono<ProductDto> create(ProductDto productDto) {
        return Mono.just(productDto)
                .map(mapper::toEntity)
                .flatMap((product) -> Mono.fromCallable(()-> repository.save(product)))
                .map(mapper::toDto);
    }

    public Mono<Page<ProductSimplifiedDto>> findAll(Pageable pageable) {
        return Mono.just(pageable)
                .flatMap(((p) -> Mono.fromCallable(() -> repository.findAll(pageable))))
                .map(products -> products.map(mapper::toSimplifiedDto));
    }
}
