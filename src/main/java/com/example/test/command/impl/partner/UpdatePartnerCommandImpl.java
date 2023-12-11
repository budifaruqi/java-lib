package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.UpdatePartnerCommandRequest;
import com.example.test.command.partner.UpdatePartnerCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PartnerCategory;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdatePartnerCommandImpl implements UpdatePartnerCommand {

  private final PartnerRepository partnerRepository;

  private final PartnerCategoryRepository partnerCategoryRepository;

  public UpdatePartnerCommandImpl(PartnerRepository partnerRepository,
      PartnerCategoryRepository partnerCategoryRepository) {
    this.partnerRepository = partnerRepository;
    this.partnerCategoryRepository = partnerCategoryRepository;
  }

  @Override
  public Mono<GetPartnerWebResponse> execute(UpdatePartnerCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkName(request))
            .flatMap(s -> checkCategory(request))
            .map(category -> updatePartner(partner, request))
            .flatMap(partnerRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<Partner> checkName(UpdatePartnerCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<PartnerCategory> checkCategory(UpdatePartnerCommandRequest request) {
    return partnerCategoryRepository.findByDeletedFalseAndId(request.getCategoryId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_CATEGORY_NOT_EXIST)));
  }

  private Mono<Partner> findPartner(UpdatePartnerCommandRequest request) {
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
        .build();
  }

  private Partner updatePartner(Partner partner, UpdatePartnerCommandRequest request) {
    partner.setName(request.getName());
    partner.setCategoryId(request.getCategoryId());
    partner.setPhone(request.getPhone());
    partner.setEmail(request.getEmail());
    partner.setAddress(request.getAddress());
    partner.setPicName(request.getPicName());
    partner.setPicPhone(request.getPicPhone());
    partner.setPicEmail(request.getPicEmail());
    partner.setIsVendor(request.getIsVendor());
    partner.setIsCustomer(request.getIsCustomer());

    return partner;
  }
}
