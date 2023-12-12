package com.example.test.repository;

import com.example.test.repository.model.Bom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BomRepository extends ReactiveMongoRepository<Bom, String>, BomRepositoryFilter {

  Mono<Boolean> existsByProductIdAndDeletedFalse(String productId);

  Mono<Bom> findByDeletedFalseAndId(String id);
}
