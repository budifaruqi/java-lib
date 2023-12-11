package com.example.test.command.impl.product.category;

import com.example.test.command.product.category.GetProductCategoryByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.model.ProductCategory;
import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetProductCategoryByIdCommandImpl implements GetProductCategoryByIdCommand {

  private final ProductCategoryRepository productCategoryRepository;

  public GetProductCategoryByIdCommandImpl(ProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  @Override
  public Mono<GetProductCategoryWebResponse> execute(String request) {
    return Mono.defer(() -> findCategory(request))
        .map(this::toGetWebResponse);
  }

  private Mono<ProductCategory> findCategory(String request) {
    return productCategoryRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_CATEGORY_NOT_EXIST)));
  }

  private GetProductCategoryWebResponse toGetWebResponse(ProductCategory productCategory) {
    return GetProductCategoryWebResponse.builder()
        .id(productCategory.getId())
        .name(productCategory.getName())
        .type(productCategory.getType())
        .isDevice(productCategory.getIsDevice())
        .achievementPoint(productCategory.getAchievementPoint())
        .memberPoint(productCategory.getMemberPoint())
        .build();
  }
}
