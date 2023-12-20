package com.example.test.repository;

import com.example.test.repository.model.PurchaseRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PurchaseRequestRepository
    extends ReactiveMongoRepository<PurchaseRequest, String>, PurchaseRequestRepositoryFilter {

  Mono<PurchaseRequest> findByDeletedFalseAndId(String id);

  Mono<PurchaseRequest> findByDeletedFalseAndVendorIdAndId(String companyId, String purchaseRequestId);
}
