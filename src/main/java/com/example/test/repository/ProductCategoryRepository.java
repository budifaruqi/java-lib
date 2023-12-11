package com.example.test.repository;

import com.example.test.repository.model.ProductCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductCategoryRepository
    extends ReactiveMongoRepository<ProductCategory, String>, ProductCategoryRepositoryFilter {

  Mono<ProductCategory> findByDeletedFalseAndName(String name);

  Mono<ProductCategory> findByDeletedFalseAndId(String request);
}
