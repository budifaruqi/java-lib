package com.example.test.repository;

import com.example.test.repository.model.BomProduction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BomProductionRepository extends ReactiveMongoRepository<BomProduction, String> {}
