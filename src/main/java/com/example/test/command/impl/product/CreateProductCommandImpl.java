package com.example.test.command.impl.product;

import com.example.test.command.model.product.CreateProductCommandRequest;
import com.example.test.command.product.CreateProductCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Brand;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductCategory;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CreateProductCommandImpl implements CreateProductCommand {

  private final ProductRepository productRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final BrandRepository brandRepository;

  public CreateProductCommandImpl(ProductRepository productRepository,
      ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<Object> execute(CreateProductCommandRequest request) {
    return Mono.defer(() -> checkCategory(request))
        .flatMap(productCategory -> checkBrand(request))
        .flatMap(brand -> checkCode(request))
        .map(check -> createProduct(request))
        .flatMap(productRepository::save);
  }

  private Mono<ProductCategory> checkCategory(CreateProductCommandRequest request) {
    return productCategoryRepository.findByDeletedFalseAndId(request.getCategoryId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.CATEGORY_NOT_EXIST)));
  }

  private Mono<Brand> checkBrand(CreateProductCommandRequest request) {
    return brandRepository.findByDeletedFalseAndId(request.getBrandId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.BRAND_NOT_EXIST)));
  }

  private Mono<Product> checkCode(CreateProductCommandRequest request) {
    return productRepository.findByDeletedFalseAndCode(request.getCode())
        .switchIfEmpty(Mono.fromSupplier(() -> Product.builder()
            .build()))
        .filter(s -> !Objects.equals(s.getCode(), request.getCode()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_CODE_ALREADY_USED)));
  }

  private Product createProduct(CreateProductCommandRequest request) {
    return Product.builder()
        .categoryId(request.getCategoryId())
        .brandId(request.getBrandId())
        .name(request.getName())
        .code(request.getCode())
        .sku(request.getSku())
        .unitOfMeasure(request.getUnitOfMeasure())
        .isActive(Boolean.TRUE)
        .description(request.getDescription())
        .imageUrls(request.getImageUrls())
        .companyShare(request.getCompanyShare())
        .build();
  }
}
