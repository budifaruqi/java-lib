package com.example.test.repository;

import com.example.test.repository.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository
    extends ReactiveMongoRepository<Transaction, String>, TransactionRepositoryFilter {}
