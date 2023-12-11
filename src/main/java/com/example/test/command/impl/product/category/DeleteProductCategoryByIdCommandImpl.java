package com.example.test.command.impl.product.category;

import com.example.test.command.product.category.DeleteProductCategoryByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.model.ProductCategory;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductCategoryByIdCommandImpl implements DeleteProductCategoryByIdCommand {

  private final ProductCategoryRepository productCategoryRepository;

  public DeleteProductCategoryByIdCommandImpl(ProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  @Override
  public Mono<Void> execute(String request) {
    return Mono.defer(() -> findCategory(request))
        .map(this::deleteCategory)
        .flatMap(productCategoryRepository::save)
        .then();
  }

  private ProductCategory deleteCategory(ProductCategory category) {
    category.setDeleted(true);

    return category;
  }

  private Mono<ProductCategory> findCategory(String request) {
    return productCategoryRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }
}
