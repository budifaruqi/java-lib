package com.example.test.repository;

import com.example.test.repository.model.Bom;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BomRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String name, String productId, Pageable pageable);

  Flux<Bom> findAllByDeletedFalseAndFilter(String name, String productId, Pageable pageable);
}
