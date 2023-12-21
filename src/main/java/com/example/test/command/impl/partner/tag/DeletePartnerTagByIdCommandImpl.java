package com.example.test.command.impl.partner.tag;

import com.example.test.command.partner.tag.DeletePartnerTagByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerTagRepository;
import com.example.test.repository.model.PartnerTag;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletePartnerTagByIdCommandImpl implements DeletePartnerTagByIdCommand {

  private final PartnerTagRepository partnerTagRepository;

  public DeletePartnerTagByIdCommandImpl(PartnerTagRepository partnerTagRepository) {
    this.partnerTagRepository = partnerTagRepository;
  }

  @Override
  public Mono<Void> execute(String request) {
    return Mono.defer(() -> findPartner(request))
        .map(this::deletePartner)
        .flatMap(partnerTagRepository::save)
        .then();
  }

  private PartnerTag deletePartner(PartnerTag partner) {
    partner.setDeleted(true);

    return partner;
  }

  private Mono<PartnerTag> findPartner(String request) {
    return partnerTagRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_TAG_NOT_EXIST)));
  }
}
