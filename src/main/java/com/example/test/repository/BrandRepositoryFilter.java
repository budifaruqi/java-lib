package com.example.test.repository;

import com.example.test.repository.model.Brand;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandRepositoryFilter {

  Flux<Brand> findAllByDeletedFalseAndFilter(String name, Pageable pageable);

  Mono<Long> countAllByDeletedFalseAndFilter(String name);
}
