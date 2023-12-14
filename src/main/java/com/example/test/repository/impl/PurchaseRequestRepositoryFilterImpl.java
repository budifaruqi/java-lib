package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.PRStatus;
import com.example.test.repository.PurchaseRequestRepositoryFilter;
import com.example.test.repository.model.PurchaseRequest;
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
public class PurchaseRequestRepositoryFilterImpl implements PurchaseRequestRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public PurchaseRequestRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String customerId, String vendorId, PRStatus status, Date startDate,
      Date endDate, Pageable pageable) {
    Query query = getQuery(customerId, vendorId, status, startDate, endDate, pageable);
    return reactiveMongoTemplate.count(query, PurchaseRequest.class, CollectionName.PURCHASE_REQUEST);
  }

  @Override
  public Flux<PurchaseRequest> findAllByDeletedFalseAndFilter(String customerId, String vendorId, PRStatus status,
      Date startDate, Date endDate, Pageable pageable) {
    Query query = getQuery(customerId, vendorId, status, startDate, endDate, pageable);
    return reactiveMongoTemplate.find(query, PurchaseRequest.class, CollectionName.PURCHASE_REQUEST);
  }

  private Query getQuery(String customerId, String vendorId, PRStatus status, Date startDate, Date endDate,
      Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andBetweenInclusive("createdDate", startDate, endDate)
        .andEqual("customerId", customerId)
        .andEqual("vendorId", vendorId)
        .andEqual("status", status)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
