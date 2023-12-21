package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.GetPartnerByIdCommandRequest;
import com.example.test.command.partner.GetPartnerByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPartnerByIdCommandImpl implements GetPartnerByIdCommand {

  private final PartnerRepository partnerRepository;

  public GetPartnerByIdCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetPartnerWebResponse> execute(GetPartnerByIdCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .map(this::toGetWebResponse);
  }

  private Mono<Partner> findPartner(GetPartnerByIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }

  private GetPartnerWebResponse toGetWebResponse(Partner partner) {
    return GetPartnerWebResponse.builder()
        .id(partner.getId())
        .name(partner.getName())
        .categoryId(partner.getCategoryId())
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
        .partnerTagList(partner.getPartnerTagList())
        .build();
  }
}
