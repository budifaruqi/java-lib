package com.example.test.repository;

import com.example.test.repository.model.Partner;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PartnerRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String categoryId, Boolean isVendor, Boolean isCustomer, String name);

  Flux<Partner> findAllByDeletedFalseAndFilter(String categoryId, Boolean isVendor, Boolean isCustomer, String name,
      Pageable pageable);
}
