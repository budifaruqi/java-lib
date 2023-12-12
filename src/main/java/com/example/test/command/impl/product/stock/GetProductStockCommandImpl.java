package com.example.test.command.impl.product.stock;

import com.example.test.command.model.product.stock.GetProductStockCommandRequest;
import com.example.test.command.product.stock.GetProductStockCommand;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.model.ProductStock;
import com.example.test.web.model.response.product.stock.GetProductStockWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetProductStockCommandImpl implements GetProductStockCommand {

  private final ProductStockRepository productStockRepository;

  private final ProductRepository productRepository;

  public GetProductStockCommandImpl(ProductStockRepository productStockRepository,
      ProductRepository productRepository) {
    this.productStockRepository = productStockRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Page<GetProductStockWebResponse>> execute(GetProductStockCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetProductStockCommandRequest request) {
    return productStockRepository.countAllByDeletedFalseAndFilter(request.getCompanyId(), request.getProductId(),
        request.getPageable());
  }

  private Mono<List<GetProductStockWebResponse>> getData(GetProductStockCommandRequest request) {
    return Flux.defer(() -> getProductStock(request))
        .map(productStock -> toGetWebResponse(productStock))
        .collectList();
  }

  private Flux<ProductStock> getProductStock(GetProductStockCommandRequest request) {
    return productStockRepository.findAllByDeletedFalseAndFilter(request.getCompanyId(), request.getProductId(),
        request.getPageable());
  }

  private GetProductStockWebResponse toGetWebResponse(ProductStock productStock) {
    return GetProductStockWebResponse.builder()
        .id(productStock.getId())
        .productId(productStock.getProductId())
        .companyId(productStock.getCompanyId())
        .stock(productStock.getStock())
        .hpp(productStock.getHpp())
        .retailPrice(productStock.getRetailPrice())
        .groceryPrice(productStock.getGroceryPrice())
        .build();
  }

  private PageImpl<GetProductStockWebResponse> toPageResponse(GetProductStockCommandRequest request,
      List<GetProductStockWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
