package com.example.test.repository;

import com.example.test.repository.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String>, ProductRepositoryFilter {

  Flux<Product> findAllByDeletedFalse();

  Mono<Product> findByDeletedFalseAndCode(String code);

  Mono<Product> findByDeletedFalseAndId(String id);
}
