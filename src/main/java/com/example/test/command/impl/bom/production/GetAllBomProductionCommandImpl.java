package com.example.test.command.impl.bom.production;

import com.example.test.command.bom.production.GetAllBomProductionCommand;
import com.example.test.command.model.bom.production.GetAllBomProductionCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BomProductionRepository;
import com.example.test.repository.BomRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Bom;
import com.example.test.repository.model.BomProduction;
import com.example.test.repository.model.Product;
import com.example.test.web.model.response.bom.production.GetBomProductionWebResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetAllBomProductionCommandImpl implements GetAllBomProductionCommand {

  private final BomProductionRepository bomProductionRepository;

  private final BomRepository bomRepository;

  private final ProductRepository productRepository;

  public GetAllBomProductionCommandImpl(BomProductionRepository bomProductionRepository, BomRepository bomRepository,
      ProductRepository productRepository) {
    this.bomProductionRepository = bomProductionRepository;
    this.bomRepository = bomRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Page<GetBomProductionWebResponse>> execute(GetAllBomProductionCommandRequest request) {
    return Mono.zip(getData(request), count(request))
        .map(objects -> toPageResponse(request, objects.getT1(), objects.getT2()));
  }

  private Mono<Long> count(GetAllBomProductionCommandRequest request) {
    return bomProductionRepository.countAllByDeletedFalseAndFilter(request.getCompanyId(), request.getTransactionId(),
        request.getStartDate(), request.getEndDate(), request.getPageable());
  }

  private Mono<List<GetBomProductionWebResponse>> getData(GetAllBomProductionCommandRequest request) {
    return Flux.defer(() -> getBomProduction(request))
        .flatMapSequential(bomProduction -> Mono.defer(() -> getBom(bomProduction))
            .flatMap(bom -> Mono.defer(() -> getProduct(bom))
                .map(product -> toGetWebResponse(bomProduction, bom, product))))
        .collectList();
  }

  private Flux<BomProduction> getBomProduction(GetAllBomProductionCommandRequest request) {
    return bomProductionRepository.findAllByDeletedFalseAndFilter(request.getCompanyId(), request.getTransactionId(),
        request.getStartDate(), request.getEndDate(), request.getPageable());
  }

  private Mono<Bom> getBom(BomProduction bomProduction) {
    return bomRepository.findByDeletedFalseAndId(bomProduction.getBomId())
        .switchIfEmpty(Mono.fromSupplier(() -> Bom.builder()
            .name(ErrorCode.BOM_NOT_EXIST)
            .build()));
  }

  private Mono<Product> getProduct(Bom bom) {
    return productRepository.findByDeletedFalseAndId(bom.getProductId())
        .switchIfEmpty(Mono.fromSupplier(() -> Product.builder()
            .name(ErrorCode.PRODUCT_NOT_EXIST)
            .build()));
  }

  private GetBomProductionWebResponse toGetWebResponse(BomProduction bomProduction, Bom bom, Product product) {
    return GetBomProductionWebResponse.builder()
        .id(bomProduction.getId())
        .companyId(bomProduction.getCompanyId())
        .transactionId(bomProduction.getTransactionId())
        .bomId(bom.getId())
        .name(bom.getName())
        .productName(product.getName())
        .materialList(bom.getMaterialList())
        .qty(bomProduction.getQty())
        .amount(bomProduction.getAmount())
        .build();
  }

  private PageImpl<GetBomProductionWebResponse> toPageResponse(GetAllBomProductionCommandRequest request,
      List<GetBomProductionWebResponse> contents, Long total) {
    return new PageImpl<>(contents, request.getPageable(), total);
  }
}
