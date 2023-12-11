package com.example.test.command.impl.product;

import com.example.test.command.model.product.UpdateProductCommandRequest;
import com.example.test.command.product.UpdateProductCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Brand;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductCategory;
import com.example.test.web.model.response.product.GetProductWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UpdateProductCommandImpl implements UpdateProductCommand {

  private final ProductRepository productRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final BrandRepository brandRepository;

  public UpdateProductCommandImpl(ProductRepository productRepository,
      ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<GetProductWebResponse> execute(UpdateProductCommandRequest request) {
    return Mono.defer(() -> findProduct(request))
        .flatMap(product -> Mono.defer(() -> checkCode(request, product))
            .flatMap(check -> checkCategory(request))
            .flatMap(productCategory -> Mono.defer(() -> checkBrand(request))
                .flatMap(brand -> Mono.fromSupplier(() -> updateProduct(request, product))
                    .flatMap(productRepository::save)
                    .map(newProduct -> toGetWebResponse(newProduct, productCategory, brand)))));
  }

  private Mono<Product> findProduct(UpdateProductCommandRequest request) {
    return productRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)));
  }

  private Mono<ProductCategory> checkCategory(UpdateProductCommandRequest request) {
    return productCategoryRepository.findByDeletedFalseAndId(request.getCategoryId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.CATEGORY_NOT_EXIST)));
  }

  private Mono<Brand> checkBrand(UpdateProductCommandRequest request) {
    return brandRepository.findByDeletedFalseAndId(request.getBrandId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.BRAND_NOT_EXIST)));
  }

  private Mono<Product> checkCode(UpdateProductCommandRequest request, Product product) {
    return productRepository.findByDeletedFalseAndCode(request.getCode())
        .switchIfEmpty(Mono.fromSupplier(() -> Product.builder()
            .build()))
        .filter(s -> s.getId() == null || Objects.equals(s.getId(), product.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_CODE_ALREADY_USED)));
  }

  private Product updateProduct(UpdateProductCommandRequest request, Product product) {
    product.setCategoryId(request.getCategoryId());
    product.setBrandId(request.getBrandId());
    product.setName(request.getName());
    product.setCode(request.getCode());
    product.setSku(request.getSku());
    product.setUnitOfMeasure(request.getUnitOfMeasure());
    product.setIsActive(request.getIsActive());
    product.setDescription(request.getDescription());
    product.setImageUrls(request.getImageUrls());

    return product;
  }

  private GetProductWebResponse toGetWebResponse(Product product, ProductCategory category, Brand brand) {
    return GetProductWebResponse.builder()
        .id(product.getId())
        .categoryId(product.getCategoryId())
        .categoryName(category.getName())
        .brandId(product.getBrandId())
        .brandName(brand.getName())
        .name(product.getName())
        .code(product.getCode())
        .sku(product.getSku())
        .unitOfMeasure(product.getUnitOfMeasure())
        .isActive(product.getIsActive())
        .description(product.getDescription())
        .imageUrls(product.getImageUrls())
        .build();
  }
}

