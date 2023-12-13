package com.example.test.command.impl.product;

import com.example.test.command.model.product.GetProductByIdCommandRequest;
import com.example.test.command.product.GetProductByIdCommand;
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

@Service
public class GetProductByIdCommandImpl implements GetProductByIdCommand {

  private final ProductRepository productRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final BrandRepository brandRepository;

  public GetProductByIdCommandImpl(ProductRepository productRepository,
      ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<GetProductWebResponse> execute(GetProductByIdCommandRequest request) {
    return Mono.defer(() -> getProduct(request))
        .flatMap(product -> Mono.defer(() -> getCategory(product))
            .flatMap(productCategory -> Mono.defer(() -> getBrand(product))
                .map(brand -> toGetWebResponse(product, productCategory, brand))));
  }

  private Mono<Product> getProduct(GetProductByIdCommandRequest request) {
    return productRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)));
  }

  private Mono<ProductCategory> getCategory(Product product) {
    return productCategoryRepository.findByDeletedFalseAndId(product.getCategoryId())
        .switchIfEmpty(Mono.fromSupplier(() -> ProductCategory.builder()
            .name(ErrorCode.CATEGORY_NOT_EXIST)
            .build()));
  }

  private Mono<Brand> getBrand(Product product) {
    return brandRepository.findByDeletedFalseAndId(product.getBrandId())
        .switchIfEmpty(Mono.fromSupplier(() -> Brand.builder()
            .name(ErrorCode.BRAND_NOT_EXIST)
            .build()));
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
        .companyShare(product.getCompanyShare())
        .build();
  }
}
