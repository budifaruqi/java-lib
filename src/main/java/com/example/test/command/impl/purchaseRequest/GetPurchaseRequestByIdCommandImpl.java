package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.GetPurchaseRequestByIdCommandRequest;
import com.example.test.command.purchaseRequest.GetPurchaseRequestByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.vo.PartnerVO;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PurchaseRequest;
import com.example.test.web.model.response.purchaseRequest.GetPurchaseRequestWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPurchaseRequestByIdCommandImpl implements GetPurchaseRequestByIdCommand {

  private final PurchaseRequestRepository purchaseRequestRepository;

  private final PartnerRepository partnerRepository;

  public GetPurchaseRequestByIdCommandImpl(PurchaseRequestRepository purchaseRequestRepository,
      PartnerRepository partnerRepository) {
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetPurchaseRequestWebResponse> execute(GetPurchaseRequestByIdCommandRequest request) {
    return Mono.defer(() -> getPR(request))
        .flatMap(purchaseRequest -> Mono.zip(getPartner(purchaseRequest.getVendorId()),
                getPartner(purchaseRequest.getCustomerId()))
            .map(objects -> toGetWebResponse(purchaseRequest, objects.getT1(), objects.getT2())));
  }

  private Mono<PurchaseRequest> getPR(GetPurchaseRequestByIdCommandRequest request) {
    return purchaseRequestRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)));
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
}
