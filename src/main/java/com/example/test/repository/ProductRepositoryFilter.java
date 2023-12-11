package com.example.test.repository;

import com.example.test.repository.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String categoryId, String brandId, String code, String sku, String name,
      Pageable pageable);

  Flux<Product> findAllByDeletedFalseAndFilter(String categoryId, String brandId, String code, String sku, String name,
      Pageable pageable);
}
