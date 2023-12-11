package com.example.test.repository;

import com.example.test.repository.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

  Mono<Category> findByIdAndDeletedFalse(String categoryId);

  Flux<Category> findAllByDeletedFalse();
}
