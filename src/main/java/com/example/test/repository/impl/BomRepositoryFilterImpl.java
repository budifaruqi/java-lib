package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.BomRepositoryFilter;
import com.example.test.repository.model.Bom;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class BomRepositoryFilterImpl implements BomRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public BomRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String name, String productId, Pageable pageable) {
    Query query = getQuery(name, productId, null);
    return reactiveMongoTemplate.count(query, Bom.class, CollectionName.BOM);
  }

  @Override
  public Flux<Bom> findAllByDeletedFalseAndFilter(String name, String productId, Pageable pageable) {
    Query query = getQuery(name, productId, pageable);
    return reactiveMongoTemplate.find(query, Bom.class, CollectionName.BOM);
  }

  private Query getQuery(String name, String productId, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("productId", productId)
        .andLikeIgnoreCase("name", name)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
