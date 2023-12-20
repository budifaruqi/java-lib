package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.UpdatePurchaseRequestStatusByIdCommandRequest;
import com.example.test.command.purchaseRequest.UpdatePurchaseRequestStatusByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.model.Partner;
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

  private final PartnerRepository partnerRepository;

  public UpdatePurchaseRequestStatusByIdCommandImpl(PurchaseRequestRepository purchaseRequestRepository,
      ProductStockRepository productStockRepository, PartnerRepository partnerRepository) {
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.productStockRepository = productStockRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Object> execute(UpdatePurchaseRequestStatusByIdCommandRequest request) {
    return Mono.defer(() -> findPR(request))
        .flatMap(purchaseRequest -> Mono.defer(() -> getVendor(purchaseRequest))
            .flatMap(vendor -> Mono.fromSupplier(() -> updateStatus(purchaseRequest, request, vendor))
                .flatMap(newPurchaseRequest -> Mono.fromSupplier(() -> newPurchaseRequest)
                    .filter(s -> s.getStatus() == PRStatus.CANCELED || s.getStatus() == PRStatus.REJECTED)
                    .flatMap(purchaseRequest1 -> reverseStock(purchaseRequest1, vendor))
                    .switchIfEmpty(Mono.fromSupplier(() -> newPurchaseRequest)))
                .flatMap(purchaseRequestRepository::save)));
  }

  private Mono<PurchaseRequest> findPR(UpdatePurchaseRequestStatusByIdCommandRequest request) {
    return purchaseRequestRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(purchaseRequest -> purchaseRequest.getStatus() == PRStatus.PENDING)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)));
  }

  private Mono<Partner> getVendor(PurchaseRequest purchaseRequest) {
    return partnerRepository.findByDeletedFalseAndId(purchaseRequest.getVendorId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsVendor)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_VENDOR)));
  }

  private PurchaseRequest updateStatus(PurchaseRequest purchaseRequest,
      UpdatePurchaseRequestStatusByIdCommandRequest request, Partner vendor) {
    purchaseRequest.setStatus(request.getStatus());

    return purchaseRequest;
  }

  private Mono<PurchaseRequest> reverseStock(PurchaseRequest purchaseRequest, Partner vendor) {
    return Flux.fromIterable(purchaseRequest.getProductList())
        .flatMapSequential(productRequest -> findProductStockReverse(vendor, productRequest))
        .collectList()
        .map(s -> purchaseRequest);
  }

  private Mono<ProductStock> findProductStockReverse(Partner vendor, ProductRequest productRequest) {
    if (vendor.getIsInternal()) {
      return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(vendor.getCompanyId(),
              productRequest.getProductId())
          .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)))
          .map(productStock -> addStock(productStock, productRequest))
          .flatMap(productStockRepository::save);
    }
    return Mono.fromSupplier(() -> ProductStock.builder()
        .build());
  }

  private Mono<ProductStock> findProductStock(PurchaseRequest request, ProductRequest productRequest, Partner vendor) {
    return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(vendor.getCompanyId(),
            productRequest.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)));
  }

  private ProductStock addStock(ProductStock productStock, ProductRequest productRequest) {
    productStock.setStock(productStock.getStock() + productRequest.getQty());

    return productStock;
  }
}
