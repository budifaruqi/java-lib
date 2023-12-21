package com.example.test.command.impl.partner.tag;

import com.example.test.command.model.partner.tag.CreatePartnerTagCommandRequest;
import com.example.test.command.partner.tag.CreatePartnerTagCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerTagRepository;
import com.example.test.repository.model.PartnerTag;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreatePartnerTagCommandImpl implements CreatePartnerTagCommand {

  private final PartnerTagRepository partnerTagRepository;

  public CreatePartnerTagCommandImpl(PartnerTagRepository partnerTagRepository) {
    this.partnerTagRepository = partnerTagRepository;
  }

  @Override
  public Mono<Object> execute(CreatePartnerTagCommandRequest request) {
    return Mono.defer(() -> checkRequest(request))
        .map(s -> toPartnerCategory(request))
        .flatMap(partnerTagRepository::save);
  }

  private Mono<PartnerTag> checkRequest(CreatePartnerTagCommandRequest request) {
    return partnerTagRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> PartnerTag.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private PartnerTag toPartnerCategory(CreatePartnerTagCommandRequest request) {
    return PartnerTag.builder()
        .name(request.getName())
        .build();
  }
}
