package com.example.test.repository;

import com.example.test.repository.model.ProductStock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductStockRepository
    extends ReactiveMongoRepository<ProductStock, String>, ProductStockRepositoryFilter {

  Mono<Boolean> existsByCompanyIdAndProductIdAndDeletedFalse(String companyId, String productId);

  Mono<ProductStock> findByDeletedFalseAndId(String id);

  Mono<ProductStock> findByDeletedFalseAndCompanyIdAndProductId(String companyId, String productId);
}
