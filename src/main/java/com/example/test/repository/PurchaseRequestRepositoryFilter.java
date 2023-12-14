package com.example.test.repository;

import com.example.test.common.enums.PRStatus;
import com.example.test.repository.model.PurchaseRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface PurchaseRequestRepositoryFilter {

  Mono<Long> countAllByDeletedFalseAndFilter(String customerId, String vendorId, PRStatus status, Date startDate,
      Date endDate, Pageable pageable);

  Flux<PurchaseRequest> findAllByDeletedFalseAndFilter(String customerId, String vendorId, PRStatus status,
      Date startDate, Date endDate, Pageable pageable);
}
