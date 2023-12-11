package com.example.test.repository;

import com.example.test.common.enums.ProductType;
import com.example.test.repository.model.ProductCategory;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductCategoryRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String name, ProductType type, Boolean isDevice);

  Flux<ProductCategory> findAllByDeletedFalseAndFilter(String name, ProductType type, Boolean isDevice,
      Pageable pageable);
}
