package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.BrandRepositoryFilter;
import com.example.test.repository.model.Brand;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class BrandRepositoryFilterImpl implements BrandRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public BrandRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String name) {
    Query query = getBrandQuery(name, null);
    return reactiveMongoTemplate.count(query, Brand.class, CollectionName.BRAND);
  }

  @Override
  public Flux<Brand> findAllByDeletedFalseAndFilter(String name, Pageable pageable) {
    Query query = getBrandQuery(name, pageable);
    return reactiveMongoTemplate.find(query, Brand.class, CollectionName.BRAND);
  }

  private Query getBrandQuery(String name, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andLikeIgnoreCase("name", name)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
