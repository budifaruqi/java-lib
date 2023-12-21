package com.example.test.command.impl.product;

import com.example.test.command.model.product.GetProductCommandRequest;
import com.example.test.command.product.GetAllProductCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Brand;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductCategory;
import com.example.test.web.model.response.product.GetProductWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllProductCommandImpl implements GetAllProductCommand {

  private final ProductRepository productRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final BrandRepository brandRepository;

  public GetAllProductCommandImpl(ProductRepository productRepository,
      ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.brandRepository = brandRepository;
  }

  @Override
  public Mono<Page<GetProductWebResponse>> execute(GetProductCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetProductCommandRequest request) {
    return productRepository.countAllByDeletedFalseAndFilter(request.getCategoryId(), request.getBrandId(),
        request.getCode(), request.getSku(), request.getName(), request.getPageable());
  }

  private Mono<List<GetProductWebResponse>> getData(GetProductCommandRequest request) {
    return Flux.defer(() -> getProduct(request))
        .flatMapSequential(product -> Mono.zip(getCategory(product), getBrand(product))
            .map(objects -> toGetWebResponse(product, objects.getT1(), objects.getT2())))
        .collectList();
  }

  private Flux<Product> getProduct(GetProductCommandRequest request) {
    return productRepository.findAllByDeletedFalseAndFilter(request.getCategoryId(), request.getBrandId(),
        request.getCode(), request.getSku(), request.getName(), request.getPageable());
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
        .productTagList(product.getProductTagList())
        .build();
  }

  private PageImpl<GetProductWebResponse> toPageResponse(GetProductCommandRequest request,
      List<GetProductWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
