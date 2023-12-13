package com.example.test.repository.impl;

import com.example.test.repository.PurchaseRequestRepositoryFilter;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseRequestRepositoryFilterImpl implements PurchaseRequestRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public PurchaseRequestRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }
}
