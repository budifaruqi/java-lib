package com.example.test.command.impl.partner;

import com.example.test.command.partner.DeletePartnerByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.model.Partner;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletePartnerByIdCommandImpl implements DeletePartnerByIdCommand {

  private final PartnerRepository partnerRepository;

  public DeletePartnerByIdCommandImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Void> execute(String request) {
    return Mono.defer(() -> findPartner(request))
        .map(this::deletePartner)
        .flatMap(partnerRepository::save)
        .then();
  }

  private Partner deletePartner(Partner partner) {
    partner.setDeleted(true);

    return partner;
  }

  private Mono<Partner> findPartner(String request) {
    return partnerRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }
}
