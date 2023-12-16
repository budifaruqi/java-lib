package com.example.test.command.impl.bom.production;

import com.example.test.command.bom.production.CreateBomProductionCommand;
import com.example.test.command.model.bom.production.CreateBomProductionCommandRequest;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.MaterialVO;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.BomProductionRepository;
import com.example.test.repository.BomRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.Bom;
import com.example.test.repository.model.BomProduction;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductStock;
import com.example.test.repository.model.Transaction;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class CreateBomProductionCommandImpl implements CreateBomProductionCommand {

  private final BomRepository bomRepository;

  private final BomProductionRepository bomProductionRepository;

  private final ProductStockRepository productStockRepository;

  private final ProductRepository productRepository;

  private final TransactionRepository transactionRepository;

  public CreateBomProductionCommandImpl(BomRepository bomRepository, BomProductionRepository bomProductionRepository,
      ProductStockRepository productStockRepository, ProductRepository productRepository,
      TransactionRepository transactionRepository) {
    this.bomRepository = bomRepository;
    this.bomProductionRepository = bomProductionRepository;
    this.productStockRepository = productStockRepository;
    this.productRepository = productRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Mono<Object> execute(CreateBomProductionCommandRequest request) {
    return Mono.fromSupplier(this::generateTransactionId)
        .flatMap(transactionId -> Mono.defer(() -> findBom(request))
            .flatMap(bom -> Mono.defer(() -> validateProduct(bom, request))
                .flatMap(product -> Mono.fromSupplier(() -> toBomProduction(transactionId, bom, request, product))
                    .flatMap(bomProductionRepository::save)
                    .map(bomProduction -> toTransaction(bomProduction, transactionId, product))
                    .flatMap(transactionRepository::save))));
  }

  private String generateTransactionId() {
    return String.valueOf(new ObjectId());
  }

  private Mono<Bom> findBom(CreateBomProductionCommandRequest request) {
    return bomRepository.findByDeletedFalseAndId(request.getBomId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.BOM_NOT_EXIST)));
  }

  private Mono<ProductRequest> validateProduct(Bom bom, CreateBomProductionCommandRequest request) {
    return Mono.defer(() -> checkProduct(bom.getProductId(), request))
        .flatMap(product -> Mono.defer(() -> validateMaterial(bom, request))
            .flatMap(productStocks -> Mono.fromSupplier(() -> getMaterialPrice(productStocks))
                .map(price -> toProductRequest(request, price, product))));
  }

  private Mono<Product> checkProduct(String productId, CreateBomProductionCommandRequest request) {
    return productRepository.findByDeletedFalseAndId(productId)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)))
        .filter(product -> product.getCompanyShare()
            .contains(request.getCompanyId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_AVAILABLE)));
  }

  private Mono<List<ProductStock>> validateMaterial(Bom bom, CreateBomProductionCommandRequest request) {
    return Flux.fromIterable(bom.getMaterialList())
        .flatMapSequential(materialVO -> checkMaterial(materialVO, request))
        .collectList();
  }

  private Mono<ProductStock> checkMaterial(MaterialVO materialVO, CreateBomProductionCommandRequest request) {
    return Mono.defer(() -> checkProduct(materialVO.getProductId(), request))
        .flatMap(product -> checkStock(materialVO, request));
  }

  private Mono<ProductStock> checkStock(MaterialVO materialVO, CreateBomProductionCommandRequest request) {
    Long amountNeeded = materialVO.getQty() * request.getQty();

    return productStockRepository.findByDeletedFalseAndId(materialVO.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)))
        .filter(productStock -> productStock.getStock() >= amountNeeded)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH)))
        .map(productStock -> updateStock(productStock, amountNeeded))
        .flatMap(productStockRepository::save);
  }

  private ProductStock updateStock(ProductStock productStock, Long amountNeeded) {
    productStock.setStock(productStock.getStock() - amountNeeded);

    return productStock;
  }

  private Long getMaterialPrice(List<ProductStock> productStocks) {

    return productStocks.stream()
        .mapToLong(ProductStock::getHpp)
        .sum();
  }

  private ProductRequest toProductRequest(CreateBomProductionCommandRequest request, Long price, Product product) {
    return ProductRequest.builder()
        .productId(product.getId())
        .productName(product.getName())
        .qty(request.getQty())
        .unitOfMeasure(product.getUnitOfMeasure())
        .price(price)
        .totalPrice(price * request.getQty())
        .build();
  }

  private BomProduction toBomProduction(String transactionId, Bom bom, CreateBomProductionCommandRequest request,
      ProductRequest productRequest) {
    return BomProduction.builder()
        .companyId(request.getCompanyId())
        .transactionId(transactionId)
        .bomId(bom.getId())
        .qty(request.getQty())
        .amount(productRequest.getTotalPrice())
        .build();
  }

  private Transaction toTransaction(BomProduction bomProduction, String transactionId, ProductRequest product) {
    return Transaction.builder()
        .transactionId(transactionId)
        .bomProductionId(bomProduction.getId())
        .productList(Collections.singletonList(product))
        .transactionScope(TransactionScope.INTERNAL)
        .transactionType(TransactionType.PRODUCTION)
        .transactionStatus(TransactionStatus.PROCESSED)
        .build();
  }
}
