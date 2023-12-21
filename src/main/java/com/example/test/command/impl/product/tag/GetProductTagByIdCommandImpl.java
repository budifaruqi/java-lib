package com.example.test.command.impl.product.tag;

import com.example.test.command.product.tag.GetProductTagByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductTagRepository;
import com.example.test.repository.model.ProductTag;
import com.example.test.web.model.response.product.tag.GetProductTagWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetProductTagByIdCommandImpl implements GetProductTagByIdCommand {

  private final ProductTagRepository productTagRepository;

  public GetProductTagByIdCommandImpl(ProductTagRepository productTagRepository) {
    this.productTagRepository = productTagRepository;
  }

  @Override
  public Mono<GetProductTagWebResponse> execute(String request) {
    return Mono.defer(() -> findTag(request))
        .map(this::toGetWebResponse);
  }

  private Mono<ProductTag> findTag(String request) {
    return productTagRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_TAG_NOT_EXIST)));
  }

  private GetProductTagWebResponse toGetWebResponse(ProductTag productTag) {
    return GetProductTagWebResponse.builder()
        .id(productTag.getId())
        .name(productTag.getName())
        .build();
  }
}
