package com.example.test.command.impl.partner.tag;

import com.example.test.command.model.partner.tag.UpdatePartnerTagCommandRequest;
import com.example.test.command.partner.tag.UpdatePartnerTagCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerTagRepository;
import com.example.test.repository.model.PartnerTag;
import com.example.test.web.model.response.partner.tag.GetPartnerTagWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdatePartnerTagCommandImpl implements UpdatePartnerTagCommand {

  private final PartnerTagRepository partnerTagRepository;

  public UpdatePartnerTagCommandImpl(PartnerTagRepository partnerTagRepository) {
    this.partnerTagRepository = partnerTagRepository;
  }

  @Override
  public Mono<GetPartnerTagWebResponse> execute(UpdatePartnerTagCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkRequest(request, partner))
            .map(s -> updatePartner(partner, request))
            .flatMap(partnerTagRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<PartnerTag> checkRequest(UpdatePartnerTagCommandRequest request, PartnerTag partner) {
    return partnerTagRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> PartnerTag.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<PartnerTag> findPartner(UpdatePartnerTagCommandRequest request) {
    return partnerTagRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_TAG_NOT_EXIST)));
  }

  private GetPartnerTagWebResponse toGetWebResponse(PartnerTag partnerTag) {
    return GetPartnerTagWebResponse.builder()
        .id(partnerTag.getId())
        .build();
  }

  private PartnerTag updatePartner(PartnerTag partnerTag, UpdatePartnerTagCommandRequest request) {
    partnerTag.setName(request.getName());

    return partnerTag;
  }
}
