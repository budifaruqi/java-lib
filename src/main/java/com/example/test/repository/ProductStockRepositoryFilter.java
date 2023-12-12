package com.example.test.repository;

import com.example.test.repository.model.ProductStock;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductStockRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String productId, Pageable pageable);

  Flux<ProductStock> findAllByDeletedFalseAndFilter(String companyId, String productId, Pageable pageable);
}
