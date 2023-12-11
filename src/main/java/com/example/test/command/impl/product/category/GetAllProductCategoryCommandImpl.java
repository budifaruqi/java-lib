package com.example.test.command.impl.product.category;

import com.example.test.command.model.product.category.GetAllProductCategoryCommandRequest;
import com.example.test.command.product.category.GetAllProductCategoryCommand;
import com.example.test.repository.ProductCategoryRepository;
import com.example.test.repository.model.ProductCategory;
import com.example.test.web.model.response.product.category.GetProductCategoryWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllProductCategoryCommandImpl implements GetAllProductCategoryCommand {

  private final ProductCategoryRepository productCategoryRepository;

  public GetAllProductCategoryCommandImpl(ProductCategoryRepository productCategoryRepository) {
    this.productCategoryRepository = productCategoryRepository;
  }

  @Override
  public Mono<Page<GetProductCategoryWebResponse>> execute(GetAllProductCategoryCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllProductCategoryCommandRequest request) {
    return productCategoryRepository.countAllByDeletedFalseAndFilter(request.getName(), request.getType(),
        request.getIsDevice());
  }

  private Mono<List<GetProductCategoryWebResponse>> getData(GetAllProductCategoryCommandRequest request) {
    return Flux.defer(() -> getCategory(request))
        .map(this::toGetWebResponse)
        .collectList();
  }

  private Flux<ProductCategory> getCategory(GetAllProductCategoryCommandRequest request) {
    return productCategoryRepository.findAllByDeletedFalseAndFilter(request.getName(), request.getType(),
        request.getIsDevice(), request.getPageable());
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

  private PageImpl<GetProductCategoryWebResponse> toPageResponse(GetAllProductCategoryCommandRequest request,
      List<GetProductCategoryWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
