package com.example.test.command.impl.bom.production;

import com.example.test.command.bom.production.GetBomProductionByIdCommand;
import com.example.test.command.model.bom.production.GetBomProductionByIdCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.repository.BomProductionRepository;
import com.example.test.repository.BomRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.model.Bom;
import com.example.test.repository.model.BomProduction;
import com.example.test.repository.model.Product;
import com.example.test.web.model.response.bom.production.GetBomProductionWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetBomProductionByIdCommandImpl implements GetBomProductionByIdCommand {

  private final BomProductionRepository bomProductionRepository;

  private final BomRepository bomRepository;

  private final ProductRepository productRepository;

  public GetBomProductionByIdCommandImpl(BomProductionRepository bomProductionRepository, BomRepository bomRepository,
      ProductRepository productRepository) {
    this.bomProductionRepository = bomProductionRepository;
    this.bomRepository = bomRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<GetBomProductionWebResponse> execute(GetBomProductionByIdCommandRequest request) {
    return Mono.defer(() -> getBomProduction(request))
        .flatMap(bomProduction -> Mono.defer(() -> getBom(bomProduction))
            .flatMap(bom -> Mono.defer(() -> getProduct(bom))
                .map(product -> toGetWebResponse(bomProduction, bom, product))));
  }

  private Mono<BomProduction> getBomProduction(GetBomProductionByIdCommandRequest request) {
    return bomProductionRepository.findByDeletedFalseAndCompanyIdAndId(request.getCompanyId(), request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.BOM_PRODUCTION_NOT_EXIST)));
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
}
