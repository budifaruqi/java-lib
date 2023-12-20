package com.example.test.repository;

import com.example.test.repository.model.MainTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TransactionRepository
    extends ReactiveMongoRepository<MainTransaction, String>, TransactionRepositoryFilter {

  Mono<MainTransaction> findByDeletedFalseAndId(String id);
}
