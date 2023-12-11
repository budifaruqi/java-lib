package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.ProductType;
import com.example.test.repository.ProductCategoryRepositoryFilter;
import com.example.test.repository.model.ProductCategory;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class ProductCategoryRepositoryFilterImpl implements ProductCategoryRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public ProductCategoryRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String name, ProductType type, Boolean isDevice) {
    Query query = getQuery(name, type, isDevice, null);
    return reactiveMongoTemplate.count(query, ProductCategory.class, CollectionName.PRODUCT_CATEGORY);
  }

  @Override
  public Flux<ProductCategory> findAllByDeletedFalseAndFilter(String name, ProductType type, Boolean isDevice,
      Pageable pageable) {
    Query query = getQuery(name, type, isDevice, pageable);
    return reactiveMongoTemplate.find(query, ProductCategory.class, CollectionName.PRODUCT_CATEGORY);
  }

  private Query getQuery(String name, ProductType type, Boolean isDevice, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("type", type)
        .andEqual("isDevice", isDevice)
        .andLikeIgnoreCase("name", name)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
