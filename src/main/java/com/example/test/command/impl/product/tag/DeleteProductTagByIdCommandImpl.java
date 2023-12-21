package com.example.test.command.impl.product.tag;

import com.example.test.command.product.tag.DeleteProductTagByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductTagRepository;
import com.example.test.repository.model.ProductTag;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductTagByIdCommandImpl implements DeleteProductTagByIdCommand {

  private final ProductTagRepository productTagRepository;

  public DeleteProductTagByIdCommandImpl(ProductTagRepository productTagRepository) {
    this.productTagRepository = productTagRepository;
  }

  @Override
  public Mono<Void> execute(String request) {
    return Mono.defer(() -> findPartner(request))
        .map(this::deletePartner)
        .flatMap(productTagRepository::save)
        .then();
  }

  private ProductTag deletePartner(ProductTag partner) {
    partner.setDeleted(true);

    return partner;
  }

  private Mono<ProductTag> findPartner(String request) {
    return productTagRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_TAG_NOT_EXIST)));
  }
}
