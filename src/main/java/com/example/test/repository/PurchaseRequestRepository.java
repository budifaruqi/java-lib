package com.example.test.repository;

import com.example.test.repository.model.PurchaseRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PurchaseRequestRepository
    extends ReactiveMongoRepository<PurchaseRequest, String>, PurchaseRequestRepositoryFilter {}
