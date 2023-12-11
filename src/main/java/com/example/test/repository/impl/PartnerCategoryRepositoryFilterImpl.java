package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.PartnerCategoryRepositoryFilter;
import com.example.test.repository.model.PartnerCategory;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class PartnerCategoryRepositoryFilterImpl implements PartnerCategoryRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public PartnerCategoryRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String name) {
    Query query = getPartnerQuery(name, null);
    return reactiveMongoTemplate.count(query, PartnerCategory.class, CollectionName.PARTNER_CATEGORY);
  }

  @Override
  public Flux<PartnerCategory> findAllByDeletedFalseAndFilter(String name, Pageable pageable) {
    Query query = getPartnerQuery(name, pageable);
    return reactiveMongoTemplate.find(query, PartnerCategory.class, CollectionName.PARTNER_CATEGORY);
  }

  private Query getPartnerQuery(String name, Pageable pageable) {
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
