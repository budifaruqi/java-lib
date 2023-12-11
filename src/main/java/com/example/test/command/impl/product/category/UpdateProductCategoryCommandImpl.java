package com.example.test.command.impl.product.category;

import com.example.test.command.model.product.category.UpdateProductCategoryCommandRequest;
import com.example.test.command.product.category.UpdateProductCategoryCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.model.ProductCategory;
import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdateProductCategoryCommandImpl implements UpdateProductCategoryCommand {

  private final ProductCategoryRepository productCategoryRepository;

  public UpdateProductCategoryCommandImpl(ProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  @Override
  public Mono<GetProductCategoryWebResponse> execute(UpdateProductCategoryCommandRequest request) {
    return Mono.defer(() -> findPartner(request))
        .flatMap(partner -> Mono.defer(() -> checkRequest(request, partner))
            .map(s -> updatePartner(partner, request))
            .flatMap(productCategoryRepository::save))
        .map(this::toGetWebResponse);
  }

  private Mono<ProductCategory> checkRequest(UpdateProductCategoryCommandRequest request, ProductCategory partner) {
    return productCategoryRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> ProductCategory.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), request.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private Mono<ProductCategory> findPartner(UpdateProductCategoryCommandRequest request) {
    return productCategoryRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_CATEGORY_NOT_EXIST)));
  }

  private GetProductCategoryWebResponse toGetWebResponse(ProductCategory category) {
    return GetProductCategoryWebResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .type(category.getType())
        .isDevice(category.getIsDevice())
        .achievementPoint(category.getAchievementPoint())
        .memberPoint(category.getMemberPoint())
        .build();
  }

  private ProductCategory updatePartner(ProductCategory productCategory, UpdateProductCategoryCommandRequest request) {
    productCategory.setName(request.getName());
    productCategory.setType(request.getType());
    productCategory.setIsDevice(request.getIsDevice());
    productCategory.setAchievementPoint(request.getAchievementPoint());
    productCategory.setMemberPoint(request.getMemberPoint());

    return productCategory;
  }
}
