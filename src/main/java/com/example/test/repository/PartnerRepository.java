package com.example.test.repository;

import com.example.test.repository.model.Partner;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PartnerRepository extends ReactiveMongoRepository<Partner, String>, PartnerRepositoryFilter {

  Mono<Partner> findByDeletedFalseAndId(String id);

  Mono<Partner> findByDeletedFalseAndName(String name);

  Mono<Partner> findByDeletedFalseAndCompanyId(String companyId);
}
