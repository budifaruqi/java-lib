package com.example.test.repository;

import com.example.test.repository.model.PartnerTag;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PartnerTagRepositoryFilter {

  Flux<PartnerTag> findAllByDeletedFalseAndFilter(String name, Pageable pageable);

  Mono<Long> countAllByDeletedFalseAndFilter(String name);
}
