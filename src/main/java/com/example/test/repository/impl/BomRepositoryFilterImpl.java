package com.example.test.repository.impl;

import com.example.test.repository.BomRepositoryFilter;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BomRepositoryFilterImpl implements BomRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public BomRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }
}
