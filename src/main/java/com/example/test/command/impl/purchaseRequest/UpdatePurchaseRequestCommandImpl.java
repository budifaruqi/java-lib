package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.UpdatePurchaseRequestByIdCommandRequest;
import com.example.test.command.purchaseRequest.UpdatePurchaseRequestCommand;
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
public class UpdatePurchaseRequestCommandImpl implements UpdatePurchaseRequestCommand {

  private final ProductRepository productRepository;

  private final ProductStockRepository productStockRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  public UpdatePurchaseRequestCommandImpl(ProductRepository productRepository,
      ProductStockRepository productStockRepository, PurchaseRequestRepository purchaseRequestRepository) {
    this.productRepository = productRepository;
    this.productStockRepository = productStockRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
  }

  @Override
  public Mono<Object> execute(UpdatePurchaseRequestByIdCommandRequest request) {
    return Mono.defer(() -> getPR(request))
        .flatMap(purchaseRequest -> Mono.defer(() -> reverseStock(purchaseRequest))
            .flatMap(oldStock -> buildProduct(request, purchaseRequest))
            .map(productRequests -> updatePR(productRequests, request, purchaseRequest))
            .flatMap(purchaseRequestRepository::save));
  }

  private Mono<PurchaseRequest> getPR(UpdatePurchaseRequestByIdCommandRequest request) {
    return purchaseRequestRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(purchaseRequest -> purchaseRequest.getStatus() == PRStatus.PENDING)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)));
  }

  private Mono<List<ProductStock>> reverseStock(PurchaseRequest purchaseRequest) {
    return Flux.fromIterable(purchaseRequest.getProductList())
        .flatMapSequential(productRequest -> Mono.defer(() -> findProductStock(purchaseRequest, productRequest))
            .map(productStock -> addStock(productStock, productRequest))
            .flatMap(productStockRepository::save))
        .collectList();
  }

  private Mono<ProductStock> findProductStock(PurchaseRequest request, ProductRequest productRequest) {
    return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(request.getVendorId(),
            productRequest.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)));
  }

  private ProductStock addStock(ProductStock productStock, ProductRequest productRequest) {
    productStock.setStock(productStock.getStock() + productRequest.getQty());

    return productStock;
  }

  private Mono<List<ProductRequest>> buildProduct(UpdatePurchaseRequestByIdCommandRequest request,
      PurchaseRequest purchaseRequest) {
    return Flux.fromIterable(request.getProductList())
        .flatMapSequential(productList -> Mono.fromSupplier(() -> productList)
            .flatMap(this::validateProduct)
            .flatMap(productRequest -> Mono.zip(findProduct(productRequest),
                    findProductStock(purchaseRequest, productRequest))
                .filter(objects -> objects.getT2()
                    .getStock() >= productRequest.getQty())
                .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH)))
                .flatMap(objects -> Mono.fromSupplier(() -> updateStock(objects.getT2(), productRequest))
                    .flatMap(productStockRepository::save)
                    .map(productStock -> setProductList(objects.getT1(), objects.getT2(), productList)))))
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

  private ProductStock updateStock(ProductStock productStock, ProductRequest productRequest) {
    productStock.setStock(productStock.getStock() - productRequest.getQty());

    return productStock;
  }

  private ProductRequest setProductList(Product product, ProductStock productStock, ProductRequest productList) {
    productList.setPrice(productStock.getGroceryPrice());
    productList.setTotalPrice(productStock.getGroceryPrice() * productList.getQty());
    productList.setProductName(product.getName());
    productList.setUnitOfMeasure(product.getUnitOfMeasure());

    return productList;
  }

  private PurchaseRequest updatePR(List<ProductRequest> productRequests,
      UpdatePurchaseRequestByIdCommandRequest request, PurchaseRequest purchaseRequest) {
    purchaseRequest.setProductList(productRequests);
    purchaseRequest.setNote(request.getNote());
    return purchaseRequest;
  }
}
