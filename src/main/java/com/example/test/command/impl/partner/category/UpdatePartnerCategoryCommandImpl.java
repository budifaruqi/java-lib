package com.example.test.command.impl.partner.category;

import com.example.test.command.model.partner.category.UpdatePartnerCategoryCommandRequest;
import com.example.test.command.partner.category.UpdatePartnerCategoryCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.model.PartnerCategory;
import com.example.test.web.model.response.partner.category.GetPartnerCategoryWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdatePartnerCategoryCommandImpl implements UpdatePartnerCategoryCommand {

  private final PartnerCategoryRepository partnerCategoryRepository;

  public UpdatePartnerCategoryCommandImpl(PartnerCategoryRepository partnerCategoryRepository) {
    this.partnerCategoryRepository = partnerCategoryRepository;
  }

  @Override
  public Mono<GetPartnerCategoryWebResponse> execute(UpdatePartnerCategoryCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkRequest(request, partner))
            .map(s -> updatePartner(partner, request))
            .flatMap(partnerCategoryRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<PartnerCategory> checkRequest(UpdatePartnerCategoryCommandRequest request, PartnerCategory partner) {
    return partnerCategoryRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> PartnerCategory.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<PartnerCategory> findPartner(UpdatePartnerCategoryCommandRequest request) {
    return partnerCategoryRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_CATEGORY_NOT_EXIST)));
  }

  private GetPartnerCategoryWebResponse toGetWebResponse(PartnerCategory partnerCategory) {
    return GetPartnerCategoryWebResponse.builder()
        .id(partnerCategory.getId())
        .name(partnerCategory.getName())
        .isPoint(partnerCategory.getIsPoint())
        .isSPK(partnerCategory.getIsSPK())
        .build();
  }

  private PartnerCategory updatePartner(PartnerCategory partnerCategory, UpdatePartnerCategoryCommandRequest request) {
    partnerCategory.setName(request.getName());
    partnerCategory.setIsPoint(request.getIsPoint());
    partnerCategory.setIsSPK(request.getIsSPK());

    return partnerCategory;
  }
}
