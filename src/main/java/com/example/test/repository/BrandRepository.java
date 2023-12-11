package com.example.test.repository;

import com.example.test.repository.model.Brand;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BrandRepository extends ReactiveMongoRepository<Brand, String>, BrandRepositoryFilter {

  Mono<Brand> findByDeletedFalseAndName(String name);

  Mono<Brand> findByDeletedFalseAndId(String id);
}
