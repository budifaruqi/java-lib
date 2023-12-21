package com.example.test.command.impl.partner.tag;

import com.example.test.command.partner.tag.GetPartnerTagByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerTagRepository;
import com.example.test.repository.model.PartnerTag;
import com.example.test.web.model.response.partner.tag.GetPartnerTagWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPartnerTagByIdCommandImpl implements GetPartnerTagByIdCommand {

  private final PartnerTagRepository partnerTagRepository;

  public GetPartnerTagByIdCommandImpl(PartnerTagRepository partnerTagRepository) {
    this.partnerTagRepository = partnerTagRepository;
  }

  @Override
  public Mono<GetPartnerTagWebResponse> execute(String request) {
    return Mono.defer(() -> findTag(request))
        .map(this::toGetWebResponse);
  }

  private Mono<PartnerTag> findTag(String request) {
    return partnerTagRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_TAG_NOT_EXIST)));
  }

  private GetPartnerTagWebResponse toGetWebResponse(PartnerTag partnerTag) {
    return GetPartnerTagWebResponse.builder()
        .id(partnerTag.getId())
        .name(partnerTag.getName())
        .build();
  }
}
