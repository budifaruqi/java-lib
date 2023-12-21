package com.example.test.repository;

import com.example.test.repository.model.PartnerTag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PartnerTagRepository extends ReactiveMongoRepository<PartnerTag, String>, PartnerTagRepositoryFilter {

  Mono<PartnerTag> findByDeletedFalseAndName(String name);

  Mono<PartnerTag> findByDeletedFalseAndId(String request);
}
