package com.example.test.command.impl.product.tag;

import com.example.test.command.model.product.tag.UpdateProductTagCommandRequest;
import com.example.test.command.product.tag.UpdateProductTagCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductTagRepository;
import com.example.test.repository.model.ProductTag;
import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdateProductTagCommandImpl implements UpdateProductTagCommand {

  private final ProductTagRepository productTagRepository;

  public UpdateProductTagCommandImpl(ProductTagRepository productTagRepository) {
    this.productTagRepository = productTagRepository;
  }

  @Override
  public Mono<GetProductTagWebResponse> execute(UpdateProductTagCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkRequest(request, partner))
            .map(s -> updatePartner(partner, request))
            .flatMap(productTagRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<ProductTag> checkRequest(UpdateProductTagCommandRequest request, ProductTag partner) {
    return productTagRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> ProductTag.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<ProductTag> findPartner(UpdateProductTagCommandRequest request) {
    return productTagRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_TAG_NOT_EXIST)));
  }

  private GetProductTagWebResponse toGetWebResponse(ProductTag productTag) {
    return GetProductTagWebResponse.builder()
        .id(productTag.getId())
        .build();
  }

  private ProductTag updatePartner(ProductTag productTag, UpdateProductTagCommandRequest request) {
    productTag.setName(request.getName());

    return productTag;
  }
}
