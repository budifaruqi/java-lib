package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.BomProductionRepositoryFilter;
import com.example.test.repository.model.BomProduction;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@Repository
public class BomProductionRepositoryFilterImpl implements BomProductionRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public BomProductionRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String transactionId, Date startDate,
      Date endDate, Pageable pageable) {
    Query query = getQuery(companyId, transactionId, startDate, endDate, null);
    return reactiveMongoTemplate.count(query, BomProduction.class, CollectionName.BOM_PRODUCTION);
  }

  @Override
  public Flux<BomProduction> findAllByDeletedFalseAndFilter(String companyId, String transactionId, Date startDate,
      Date endDate, Pageable pageable) {
    Query query = getQuery(companyId, transactionId, startDate, endDate, pageable);
    return reactiveMongoTemplate.find(query, BomProduction.class, CollectionName.BOM_PRODUCTION);
  }

  private Query getQuery(String companyId, String transactionId, Date startDate, Date endDate, Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("companyId", companyId)
        .andBetweenInclusive("createdDate", startDate, endDate)
        .andEqual("transactionId", transactionId)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
