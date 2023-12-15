package com.example.test.repository;

import com.example.test.repository.model.BomProduction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BomProductionRepository
    extends ReactiveMongoRepository<BomProduction, String>, BomProductionRepositoryFilter {

  Mono<BomProduction> findByDeletedFalseAndCompanyIdAndId(String companyId, String id);
}
