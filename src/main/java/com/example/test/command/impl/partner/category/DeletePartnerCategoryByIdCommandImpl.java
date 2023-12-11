package com.example.test.command.impl.partner.category;

import com.example.test.command.partner.category.DeletePartnerCategoryByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.model.PartnerCategory;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletePartnerCategoryByIdCommandImpl implements DeletePartnerCategoryByIdCommand {

  private final PartnerCategoryRepository partnerCategoryRepository;

  public DeletePartnerCategoryByIdCommandImpl(PartnerCategoryRepository partnerCategoryRepository) {
    this.partnerCategoryRepository = partnerCategoryRepository;
  }

  @Override
  public Mono<Void> execute(String request) {
    return Mono.defer(() -> findPartner(request))
        .map(this::deletePartner)
        .flatMap(partnerCategoryRepository::save)
        .then();
  }

  private PartnerCategory deletePartner(PartnerCategory partner) {
    partner.setDeleted(true);

    return partner;
  }

  private Mono<PartnerCategory> findPartner(String request) {
    return partnerCategoryRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }
}
