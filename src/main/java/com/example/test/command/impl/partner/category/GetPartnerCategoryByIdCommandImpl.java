package com.example.test.command.impl.partner.category;

import com.example.test.command.partner.category.GetPartnerCategoryByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.model.PartnerCategory;
import com.example.test.web.model.response.partner.category.GetPartnerCategoryWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPartnerCategoryByIdCommandImpl implements GetPartnerCategoryByIdCommand {

  private final PartnerCategoryRepository partnerCategoryRepository;

  public GetPartnerCategoryByIdCommandImpl(PartnerCategoryRepository partnerCategoryRepository) {
    this.partnerCategoryRepository = partnerCategoryRepository;
  }

  @Override
  public Mono<GetPartnerCategoryWebResponse> execute(String request) {
    return Mono.defer(() -> findPartnerCategory(request))
        .map(this::toGetWebResponse);
  }

  private Mono<PartnerCategory> findPartnerCategory(String id) {
    return partnerCategoryRepository.findByDeletedFalseAndId(id)
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
}
