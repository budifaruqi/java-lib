package com.example.test.repository;

import com.example.test.repository.model.ProductTag;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductTagRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String name);

  Flux<ProductTag> findAllByDeletedFalseAndFilter(String name, Pageable pageable);
}
