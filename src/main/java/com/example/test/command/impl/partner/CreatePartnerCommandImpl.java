package com.example.test.command.impl.partner;

import com.example.test.command.model.partner.CreatePartnerCommandRequest;
import com.example.test.command.partner.CreatePartnerCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.CompanyRepository;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Company;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PartnerCategory;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreatePartnerCommandImpl implements CreatePartnerCommand {

  private final PartnerRepository partnerRepository;

  private final PartnerCategoryRepository partnerCategoryRepository;

  private final CompanyRepository companyRepository;

  public CreatePartnerCommandImpl(PartnerRepository partnerRepository,
      PartnerCategoryRepository partnerCategoryRepository, CompanyRepository companyRepository) {
    this.partnerRepository = partnerRepository;
    this.partnerCategoryRepository = partnerCategoryRepository;
    this.companyRepository = companyRepository;
  }

  @Override
  public Mono<Object> execute(CreatePartnerCommandRequest request) {
    return Mono.defer(() -> checkCompany(request))
        .flatMap(company -> checkName(request))
        .flatMap(s -> checkCategory(request))
        .map(category -> toPartner(request))
        .flatMap(partnerRepository::save);
  }

  private Mono<Company> checkCompany(CreatePartnerCommandRequest request) {
    if (request.getIsInternal()) {
      return companyRepository.findByIdAndDeletedFalse(request.getCompanyId())
          .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.COMPANY_NOT_FOUND)));
    }
    return Mono.fromSupplier(() -> Company.builder()
        .build());
  }

  private Mono<Partner> checkName(CreatePartnerCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> Partner.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<PartnerCategory> checkCategory(CreatePartnerCommandRequest request) {
    return partnerCategoryRepository.findByDeletedFalseAndId(request.getCategoryId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_CATEGORY_NOT_EXIST)));
  }

  private Partner toPartner(CreatePartnerCommandRequest request) {
    return Partner.builder()
        .name(request.getName())
        .categoryId(request.getCategoryId())
        .phone(request.getPhone())
        .email(request.getEmail())
        .address(request.getAddress())
        .picName(request.getPicName())
        .picPhone(request.getPicPhone())
        .picEmail(request.getPicEmail())
        .isVendor(request.getIsVendor())
        .isCustomer(request.getIsCustomer())
        .isInternal(request.getIsInternal())
        .companyId(request.getCompanyId())
        .build();
  }
}
