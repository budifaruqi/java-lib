package com.example.test.command.impl.partner.tag;

import com.example.test.command.model.partner.tag.GetAllPartnerTagCommandRequest;
import com.example.test.command.partner.tag.GetAllPartnerTagCommand;
import com.example.test.repository.PartnerTagRepository;
import com.example.test.repository.model.PartnerTag;
import com.example.test.web.model.response.partner.tag.GetPartnerTagWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllPartnerTagCommandImpl implements GetAllPartnerTagCommand {

  private final PartnerTagRepository partnerTagRepository;

  public GetAllPartnerTagCommandImpl(PartnerTagRepository partnerTagRepository) {
    this.partnerTagRepository = partnerTagRepository;
  }

  @Override
  public Mono<Page<GetPartnerTagWebResponse>> execute(GetAllPartnerTagCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllPartnerTagCommandRequest request) {
    return partnerTagRepository.countAllByDeletedFalseAndFilter(request.getName());
  }

  private Mono<List<GetPartnerTagWebResponse>> getData(GetAllPartnerTagCommandRequest request) {
    return Flux.defer(() -> getPartner(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<PartnerTag> getPartner(GetAllPartnerTagCommandRequest request) {
    return partnerTagRepository.findAllByDeletedFalseAndFilter(request.getName(), request.getPageable());
  }

  private GetPartnerTagWebResponse toGetWebResponse(PartnerTag partnerTag) {
    return GetPartnerTagWebResponse.builder()
        .id(partnerTag.getId())
        .name(partnerTag.getName())
        .build();
  }

  private PageImpl<GetPartnerTagWebResponse> toPageResponse(GetAllPartnerTagCommandRequest request,
      List<GetPartnerTagWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
