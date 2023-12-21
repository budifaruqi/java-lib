package com.example.test.command.impl.product.tag;

import com.example.test.command.model.product.tag.CreateProductTagCommandRequest;
import com.example.test.command.product.tag.CreateProductTagCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductTagRepository;
import com.example.test.repository.model.ProductTag;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreateProductTagCommandImpl implements CreateProductTagCommand {

  private final ProductTagRepository productTagRepository;

  public CreateProductTagCommandImpl(ProductTagRepository productTagRepository) {
    this.productTagRepository = productTagRepository;
  }

  @Override
  public Mono<Object> execute(CreateProductTagCommandRequest request) {
    return Mono.defer(() -> checkRequest(request))
        .map(s -> toPartnerCategory(request))
        .flatMap(productTagRepository::save);
  }

  private Mono<ProductTag> checkRequest(CreateProductTagCommandRequest request) {
    return productTagRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> ProductTag.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private ProductTag toPartnerCategory(CreateProductTagCommandRequest request) {
    return ProductTag.builder()
        .name(request.getName())
        .build();
  }
}
