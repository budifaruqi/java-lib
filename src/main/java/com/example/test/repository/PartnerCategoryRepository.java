package com.example.test.repository;

import com.example.test.repository.model.PartnerCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PartnerCategoryRepository
    extends ReactiveMongoRepository<PartnerCategory, String>, PartnerCategoryRepositoryFilter {

  Mono<PartnerCategory> findByDeletedFalseAndId(String id);

  Mono<PartnerCategory> findByDeletedFalseAndName(String name);
}
