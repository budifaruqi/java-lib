package com.example.test.repository.impl;

import com.example.test.common.constant.CollectionName;
import com.example.test.repository.ProductRepositoryFilter;
import com.example.test.repository.model.Product;
import com.solusinegeri.mongodb.helper.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
public class ProductRepositoryFilterImpl implements ProductRepositoryFilter {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public ProductRepositoryFilterImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  public Mono<Long> countAllByDeletedFalseAndFilter(String categoryId, String brandId, String code, String sku,
      String name, Pageable pageable) {
    Query query = getProducts(categoryId, brandId, code, sku, name, null);
    return reactiveMongoTemplate.count(query, Product.class, CollectionName.PRODUCT);
  }

  @Override
  public Flux<Product> findAllByDeletedFalseAndFilter(String categoryId, String brandId, String code, String sku,
      String name, Pageable pageable) {
    Query query = getProducts(categoryId, brandId, code, sku, name, pageable);
    return reactiveMongoTemplate.find(query, Product.class, CollectionName.PRODUCT);
  }

  private Query getProducts(String categoryId, String brandId, String code, String sku, String name,
      Pageable pageable) {
    Query query = QueryBuilder.create()
        .andEqual("deleted", false)
        .andEqual("categoryId", categoryId)
        .andEqual("brandId", brandId)
        .andLikeIgnoreCase("code", code)
        .andLikeIgnoreCase("sku", sku)
        .andLikeIgnoreCase("name", name)
        .build();

    if (Objects.nonNull(pageable)) {
      query.with(pageable);
    }
    return query;
  }
}
