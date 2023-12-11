package com.example.test.command.impl.partner.category;

import com.example.test.command.model.partner.category.CreatePartnerCategoryCommandRequest;
import com.example.test.command.partner.category.CreatePartnerCategoryCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.PartnerCategoryRepository;
import com.example.test.repository.model.PartnerCategory;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreatePartnerCategoryCommandImpl implements CreatePartnerCategoryCommand {

  private final PartnerCategoryRepository partnerCategoryRepository;

  public CreatePartnerCategoryCommandImpl(PartnerCategoryRepository partnerCategoryRepository) {
    this.partnerCategoryRepository = partnerCategoryRepository;
  }

  @Override
  public Mono<Object> execute(CreatePartnerCategoryCommandRequest request) {
    return Mono.defer(() -> checkRequest(request))
        .map(s -> toPartnerCategory(request))
        .flatMap(partnerCategoryRepository::save);
  }

  private Mono<PartnerCategory> checkRequest(CreatePartnerCategoryCommandRequest request) {
    return partnerCategoryRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> PartnerCategory.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private PartnerCategory toPartnerCategory(CreatePartnerCategoryCommandRequest request) {
    return PartnerCategory.builder()
        .name(request.getName())
        .isSPK(request.getIsSPK())
        .isPoint(request.getIsPoint())
        .build();
  }
}
