package com.example.test.repository;

import com.example.test.repository.model.ProductTag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductTagRepository extends ReactiveMongoRepository<ProductTag, String>, ProductTagRepositoryFilter {

  Mono<ProductTag> findByDeletedFalseAndName(String name);

  Mono<ProductTag> findByDeletedFalseAndId(String request);
}
