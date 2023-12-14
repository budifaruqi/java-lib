package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.UpdatePurchaseRequestStatusByIdCommandRequest;
import com.example.test.command.purchaseRequest.UpdatePurchaseRequestStatusByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.model.ProductStock;
import com.example.test.repository.model.PurchaseRequest;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UpdatePurchaseRequestStatusByIdCommandImpl implements UpdatePurchaseRequestStatusByIdCommand {

  private final PurchaseRequestRepository purchaseRequestRepository;

  private final ProductStockRepository productStockRepository;

  public UpdatePurchaseRequestStatusByIdCommandImpl(PurchaseRequestRepository purchaseRequestRepository,
      ProductStockRepository productStockRepository) {
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.productStockRepository = productStockRepository;
  }

  @Override
  public Mono<Object> execute(UpdatePurchaseRequestStatusByIdCommandRequest request) {
    return Mono.defer(() -> findPR(request))
        .flatMap(purchaseRequest -> Mono.fromSupplier(() -> updateStatus(purchaseRequest, request))
            .flatMap(newPurchaseRequest -> Mono.fromSupplier(() -> newPurchaseRequest)
                .filter(s -> s.getStatus() == PRStatus.CANCELED || s.getStatus() == PRStatus.REJECTED)
                .flatMap(this::reverseStock)
                .switchIfEmpty(Mono.fromSupplier(() -> newPurchaseRequest)))
            .flatMap(purchaseRequestRepository::save));
  }

  private Mono<PurchaseRequest> findPR(UpdatePurchaseRequestStatusByIdCommandRequest request) {
    return purchaseRequestRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(purchaseRequest -> purchaseRequest.getStatus() == PRStatus.PENDING)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)));
  }

  private PurchaseRequest updateStatus(PurchaseRequest purchaseRequest,
      UpdatePurchaseRequestStatusByIdCommandRequest request) {
    purchaseRequest.setStatus(request.getStatus());

    return purchaseRequest;
  }

  private Mono<PurchaseRequest> reverseStock(PurchaseRequest purchaseRequest) {
    return Flux.fromIterable(purchaseRequest.getProductList())
        .flatMapSequential(productRequest -> Mono.defer(() -> findProductStock(purchaseRequest, productRequest))
            .map(productStock -> addStock(productStock, productRequest))
            .flatMap(productStockRepository::save))
        .collectList()
        .map(s -> purchaseRequest);
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
}
