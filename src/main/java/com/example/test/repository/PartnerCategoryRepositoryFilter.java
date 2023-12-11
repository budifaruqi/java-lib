package com.example.test.repository;

import com.example.test.repository.model.PartnerCategory;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PartnerCategoryRepositoryFilter {

  Flux<PartnerCategory> findAllByDeletedFalseAndFilter(String name, Pageable pageable);

  Mono<Long> countAllByDeletedFalseAndFilter(String name);
}
