package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.CreatePurchaseRequestCommandRequest;
import com.example.test.command.purchaseRequest.CreatePurchaseRequestCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductStock;
import com.example.test.repository.model.PurchaseRequest;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CreatePurchaseRequestCommandImpl implements CreatePurchaseRequestCommand {

  private final ProductRepository productRepository;

  private final ProductStockRepository productStockRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  public CreatePurchaseRequestCommandImpl(ProductRepository productRepository,
      ProductStockRepository productStockRepository, PurchaseRequestRepository purchaseRequestRepository) {
    this.productRepository = productRepository;
    this.productStockRepository = productStockRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
  }

  @Override
  public Mono<Object> execute(CreatePurchaseRequestCommandRequest request) {
    return Mono.defer(() -> buildProduct(request))
        .map(productList -> toPurchaseRequest(productList, request))
        .flatMap(purchaseRequestRepository::save);
  }

  private Mono<List<ProductRequest>> buildProduct(CreatePurchaseRequestCommandRequest request) {
    return Flux.fromIterable(request.getProductList())
        .flatMapSequential(productList -> Mono.fromSupplier(() -> productList)
            .flatMap(this::validateProduct)
            .flatMap(productRequest -> Mono.zip(findProduct(productRequest), findProductStock(request, productRequest))
                .map(objects -> setProductList(objects.getT1(), objects.getT2(), productList))))
        .collectList();
  }

  private Mono<ProductRequest> validateProduct(ProductRequest productRequest) {
    return Mono.fromSupplier(() -> productRequest)
        .filter(s -> s.getQty() > 0)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_QUANTITY_NOT_EXIST)));
  }

  private Mono<Product> findProduct(ProductRequest productRequest) {
    return productRepository.findByDeletedFalseAndId(productRequest.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_EXIST)));
  }

  private Mono<ProductStock> findProductStock(CreatePurchaseRequestCommandRequest request,
      ProductRequest productRequest) {
    return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(request.getVendorId(),
            productRequest.getProductId())
        .filter(productStock -> productStock.getStock() >= productRequest.getQty())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH)))
        .map(productStock -> updateStock(productStock, productRequest))
        .flatMap(productStockRepository::save);
  }

  private ProductRequest setProductList(Product product, ProductStock productStock, ProductRequest productList) {
    productList.setPrice(productStock.getGroceryPrice());
    productList.setTotalPrice(productStock.getGroceryPrice() * productList.getQty());
    productList.setProductName(product.getName());
    productList.setUnitOfMeasure(product.getUnitOfMeasure());

    return productList;
  }

  private ProductStock updateStock(ProductStock productStock, ProductRequest productRequest) {
    productStock.setStock(productStock.getStock() - productRequest.getQty());

    return productStock;
  }

  private PurchaseRequest toPurchaseRequest(List<ProductRequest> productList,
      CreatePurchaseRequestCommandRequest request) {
    return PurchaseRequest.builder()
        .customerId(request.getCustomerId())
        .vendorId(request.getVendorId())
        .productList(productList)
        .amountTotal(getAmountProduct(productList))
        .status(PRStatus.PENDING)
        .note(request.getNote())
        .build();
  }

  private Long getAmountProduct(List<ProductRequest> productRequestList) {
    return productRequestList.stream()
        .mapToLong(ProductRequest::getTotalPrice)
        .sum();
  }
}

