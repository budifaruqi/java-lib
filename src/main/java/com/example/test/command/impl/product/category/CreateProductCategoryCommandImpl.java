package com.example.test.command.impl.product.category;

import com.example.test.command.model.product.category.CreateProductCategoryCommandRequest;
import com.example.test.command.product.category.CreateProductCategoryCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.ProductType;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.model.ProductCategory;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreateProductCategoryCommandImpl implements CreateProductCategoryCommand {

  private final ProductCategoryRepository productCategoryRepository;

  public CreateProductCategoryCommandImpl(ProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  @Override
  public Mono<Object> execute(CreateProductCategoryCommandRequest request) {
    return Mono.defer(() -> checkRequest(request))
        .map(s -> toProductCategory(request))
        .flatMap(productCategoryRepository::save);
  }

  private Mono<ProductCategory> checkRequest(CreateProductCategoryCommandRequest request) {
    return productCategoryRepository.findByDeletedFalseAndName(request.getName())
        .switchIfEmpty(Mono.fromSupplier(() -> ProductCategory.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getName(), request.getName()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.NAME_ALREADY_USED)));
  }

  private ProductCategory toProductCategory(CreateProductCategoryCommandRequest request) {
    return ProductCategory.builder()
        .name(request.getName())
        .type(request.getType())
        .isDevice(request.getType() == ProductType.BARANG ? request.getIsDevice() : Boolean.FALSE)
        .achievementPoint(request.getAchievementPoint())
        .memberPoint(request.getMemberPoint())
        .build();
  }
}
