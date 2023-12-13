package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.GetAllPurchaseRequestCommandRequest;
import com.example.test.command.purchaseRequest.GetAllPurchaseRequestCommand;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.web.model.response.purchaseRequest.GetPurchaseRequestWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetAllPurchaseRequestCommandImpl implements GetAllPurchaseRequestCommand {

  private final PartnerRepository partnerRepository;

  private final ProductRepository productRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  public GetAllPurchaseRequestCommandImpl(PartnerRepository partnerRepository, ProductRepository productRepository,
      PurchaseRequestRepository purchaseRequestRepository) {
    this.partnerRepository = partnerRepository;
    this.productRepository = productRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
  }

  @Override
  public Mono<Page<GetPurchaseRequestWebResponse>> execute(GetAllPurchaseRequestCommandRequest paramT) {
    return null;
  }
}
