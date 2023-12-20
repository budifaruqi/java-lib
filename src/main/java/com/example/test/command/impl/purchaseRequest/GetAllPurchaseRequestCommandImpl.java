package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.GetAllPurchaseRequestCommandRequest;
import com.example.test.command.purchaseRequest.GetAllPurchaseRequestCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.vo.PartnerVO;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PurchaseRequest;
import com.example.test.web.model.response.purchaseRequest.GetPurchaseRequestWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllPurchaseRequestCommandImpl implements GetAllPurchaseRequestCommand {

  private final PartnerRepository partnerRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  public GetAllPurchaseRequestCommandImpl(PartnerRepository partnerRepository,
      PurchaseRequestRepository purchaseRequestRepository) {
    this.partnerRepository = partnerRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
  }

  @Override
  public Mono<Page<GetPurchaseRequestWebResponse>> execute(GetAllPurchaseRequestCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllPurchaseRequestCommandRequest request) {
    return purchaseRequestRepository.countAllByDeletedFalseAndFilter(request.getCustomerId(), request.getVendorId(),
        request.getStatus(), request.getStartDate(), request.getEndDate(), request.getPageable());
  }

  private Mono<List<GetPurchaseRequestWebResponse>> getData(GetAllPurchaseRequestCommandRequest request) {
    return Flux.defer(() -> getPR(request))
        .flatMapSequential(purchaseRequest -> Mono.zip(getPartner(purchaseRequest.getVendorId()),
                getPartner(purchaseRequest.getCustomerId()))
            .map(objects -> toGetWebResponse(purchaseRequest, objects.getT1(), objects.getT2())))
        .collectList();
  }

  private Flux<PurchaseRequest> getPR(GetAllPurchaseRequestCommandRequest request) {
    return purchaseRequestRepository.findAllByDeletedFalseAndFilter(request.getCustomerId(), request.getVendorId(),
        request.getStatus(), request.getStartDate(), request.getEndDate(), request.getPageable());
  }

  private Mono<PartnerVO> getPartner(String id) {
    return partnerRepository.findByDeletedFalseAndId(id)
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .name(ErrorCode.PARTNER_NOT_EXIST)
            .build()))
        .map(this::toVO);
  }

  private PartnerVO toVO(Partner partner) {
    return PartnerVO.builder()
        .id(partner.getId())
        .categoryId(partner.getCategoryId())
        .name(partner.getName())
        .phone(partner.getPhone())
        .email(partner.getEmail())
        .address(partner.getAddress())
        .picName(partner.getPicName())
        .picPhone(partner.getPicPhone())
        .picEmail(partner.getPicEmail())
        .isVendor(partner.getIsVendor())
        .isCustomer(partner.getIsCustomer())
        .isInternal(partner.getIsInternal())
        .companyId(partner.getCompanyId())
        .build();
  }

  private GetPurchaseRequestWebResponse toGetWebResponse(PurchaseRequest purchaseRequest, PartnerVO vendor,
      PartnerVO customer) {
    return GetPurchaseRequestWebResponse.builder()
        .id(purchaseRequest.getId())
        .customerId(customer.getId())
        .vendorId(vendor.getId())
        .customer(customer)
        .vendor(vendor)
        .productList(purchaseRequest.getProductList())
        .amountTotal(purchaseRequest.getAmountTotal())
        .status(purchaseRequest.getStatus())
        .note(purchaseRequest.getNote())
        .build();
  }

  private PageImpl<GetPurchaseRequestWebResponse> toPageResponse(GetAllPurchaseRequestCommandRequest request,
      List<GetPurchaseRequestWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
