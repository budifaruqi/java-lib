package com.example.test.command.impl.partner.category;

import com.example.test.command.model.partner.category.GetAllPartnerCategoryCommandRequest;
import com.example.test.command.partner.category.GetAllPartnerCategoryCommand;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.model.PartnerCategory;
import com.example.test.web.model.response.partner.category.GetPartnerCategoryWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllPartnerCategoryCommandImpl implements GetAllPartnerCategoryCommand {

  private final PartnerCategoryRepository partnerCategoryRepository;

  public GetAllPartnerCategoryCommandImpl(PartnerCategoryRepository partnerCategoryRepository) {
    this.partnerCategoryRepository = partnerCategoryRepository;
  }

  @Override
  public Mono<Page<GetPartnerCategoryWebResponse>> execute(GetAllPartnerCategoryCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllPartnerCategoryCommandRequest request) {
    return partnerCategoryRepository.countAllByDeletedFalseAndFilter(request.getName());
  }

  private Mono<List<GetPartnerCategoryWebResponse>> getData(GetAllPartnerCategoryCommandRequest request) {
    return Flux.defer(() -> getPartner(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<PartnerCategory> getPartner(GetAllPartnerCategoryCommandRequest request) {
    return partnerCategoryRepository.findAllByDeletedFalseAndFilter(request.getName(), request.getPageable());
  }

  private GetPartnerCategoryWebResponse toGetWebResponse(PartnerCategory partnerCategory) {
    return GetPartnerCategoryWebResponse.builder()
        .id(partnerCategory.getId())
        .name(partnerCategory.getName())
        .isPoint(partnerCategory.getIsPoint())
        .isSPK(partnerCategory.getIsSPK())
        .build();
  }

  private PageImpl<GetPartnerCategoryWebResponse> toPageResponse(GetAllPartnerCategoryCommandRequest request,
      List<GetPartnerCategoryWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
