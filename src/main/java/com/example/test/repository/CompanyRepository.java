package com.example.test.repository;

import com.example.test.repository.model.Company;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CompanyRepository extends ReactiveMongoRepository<Company, String> {

  Mono<Boolean> existsByDeletedFalseAndName(String request);
}
