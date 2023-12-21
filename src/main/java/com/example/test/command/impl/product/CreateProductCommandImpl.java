package com.example.test.command.impl.product;

import com.example.test.command.model.product.CreateProductCommandRequest;
import com.example.test.command.product.CreateProductCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.vo.TagVO;
import com.example.test.repository.BrandRepository;
import com.example.test.repository.CompanyRepository;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductTagRepository;
import com.example.test.repository.model.Brand;
import com.example.test.repository.model.Company;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductCategory;
import com.example.test.repository.model.ProductTag;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class CreateProductCommandImpl implements CreateProductCommand {

  private final ProductRepository productRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final BrandRepository brandRepository;

  private final ProductTagRepository productTagRepository;

  private final CompanyRepository companyRepository;

  public CreateProductCommandImpl(ProductRepository productRepository,
      ProductCategoryRepository productCategoryRepository, BrandRepository brandRepository,
      ProductTagRepository productTagRepository, CompanyRepository companyRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
    this.brandRepository = brandRepository;
    this.productTagRepository = productTagRepository;
    this.companyRepository = companyRepository;
  }

  @Override
  public Mono<Object> execute(CreateProductCommandRequest request) {
    return Mono.defer(() -> checkCategory(request))
        .flatMap(productCategory -> checkBrand(request))
        .flatMap(brand -> checkCode(request))
        .flatMap(code -> checkCompany(request))
        .flatMap(company -> getTags(request))
        .map(tags -> createProduct(request, tags))
        .flatMap(productRepository::save);
  }

  private Mono<List<Company>> checkCompany(CreateProductCommandRequest request) {
    return Flux.fromIterable(request.getCompanyShare())
        .flatMapSequential(this::getCompany)
        .collectList();
  }

  private Mono<Company> getCompany(String id) {
    return companyRepository.findByIdAndDeletedFalse(id)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.COMPANY_NOT_FOUND)));
  }

  private Mono<List<TagVO>> getTags(CreateProductCommandRequest request) {
    return Flux.fromIterable(request.getProductTagIds())
        .flatMapSequential(this::getTagsName)
        .map(this::toTagVO)
        .collectList();
  }

  private Mono<ProductTag> getTagsName(String id) {
    return productTagRepository.findByDeletedFalseAndId(id)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_TAG_NOT_EXIST)));
  }

  private TagVO toTagVO(ProductTag partnerTag) {
    return TagVO.builder()
        .id(partnerTag.getId())
        .name(partnerTag.getName())
        .build();
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

  private Product createProduct(CreateProductCommandRequest request, List<TagVO> tags) {
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
        .productTagList(tags)
        .build();
  }
}
