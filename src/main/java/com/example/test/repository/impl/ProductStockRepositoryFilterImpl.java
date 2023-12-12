package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.ProductStockRepositoryFilter;
import com.example.test.repository.model.ProductStock;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class ProductStockRepositoryFilterImpl implements ProductStockRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public ProductStockRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String productId, Pageable pageable) {
    Query query = getProductStock(companyId, productId, null);
    return reactiveMongoTemplate.count(query, ProductStock.class, CollectionName.PRODUCT_STOCK);
  }

  @Override
  public Flux<ProductStock> findAllByDeletedFalseAndFilter(String companyId, String productId, Pageable pageable) {
    Query query = getProductStock(companyId, productId, pageable);
    return reactiveMongoTemplate.find(query, ProductStock.class, CollectionName.PRODUCT_STOCK);
  }

  private Query getProductStock(String companyId, String productId, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyId", companyId)
        .andEqual("productId", productId)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
