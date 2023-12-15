package com.example.test.repository;

import com.example.test.repository.model.BomProduction;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface BomProductionRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String companyId, String transactionId, Date startDate, Date endDate,
      Pageable pageable);

  Flux<BomProduction> findAllByDeletedFalseAndFilter(String companyId, String transactionId, Date startDate,
      Date endDate, Pageable pageable);
}
