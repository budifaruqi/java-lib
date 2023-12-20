package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.UpdatePurchaseRequestByIdCommandRequest;
import com.example.test.command.purchaseRequest.UpdatePurchaseRequestCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.enums.PriceType;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.model.Partner;
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

  private final PartnerRepository partnerRepository;

  public UpdatePurchaseRequestCommandImpl(ProductRepository productRepository,
      ProductStockRepository productStockRepository, PurchaseRequestRepository purchaseRequestRepository,
      PartnerRepository partnerRepository) {
    this.productRepository = productRepository;
    this.productStockRepository = productStockRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Object> execute(UpdatePurchaseRequestByIdCommandRequest request) {
    return Mono.defer(() -> getPR(request))
        .flatMap(purchaseRequest -> Mono.defer(() -> getVendor(purchaseRequest))
            .flatMap(vendor -> Mono.defer(() -> reverseStock(purchaseRequest, vendor))
                .flatMap(oldStock -> buildProduct(request, purchaseRequest, vendor))
                .map(productRequests -> updatePR(productRequests, request, purchaseRequest))
                .flatMap(purchaseRequestRepository::save)));
  }

  private Mono<PurchaseRequest> getPR(UpdatePurchaseRequestByIdCommandRequest request) {
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

  private Mono<List<ProductStock>> reverseStock(PurchaseRequest purchaseRequest, Partner vendor) {
    return Flux.fromIterable(purchaseRequest.getProductList())
        .flatMapSequential(productRequest -> findProductStockReverse(vendor, productRequest))
        .collectList();
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

  private Mono<ProductStock> findProductStock(Partner vendor, ProductRequest productRequest) {
    if (vendor.getIsInternal()) {
      return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(vendor.getCompanyId(),
              productRequest.getProductId())
          .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)))
          .filter(productStock -> productStock.getStock() >= productRequest.getQty())
          .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH)))
          .map(productStock -> updateStock(productStock, productRequest))
          .flatMap(productStockRepository::save);
    }
    return Mono.fromSupplier(() -> ProductStock.builder()
        .groceryPrice(productRequest.getPrice())
        .build());
  }

  private ProductStock addStock(ProductStock productStock, ProductRequest productRequest) {
    productStock.setStock(productStock.getStock() + productRequest.getQty());

    return productStock;
  }

  private Mono<List<ProductRequest>> buildProduct(UpdatePurchaseRequestByIdCommandRequest request,
      PurchaseRequest purchaseRequest, Partner vendor) {
    return Flux.fromIterable(request.getProductList())
        .flatMapSequential(productList -> Mono.fromSupplier(() -> productList)
            .flatMap(this::validateProduct)
            .flatMap(productRequest -> Mono.defer(() -> findProduct(productRequest))
                .flatMap(product -> Mono.defer(() -> findProductStock(vendor, productRequest))
                    .map(productStock -> setProductPrice(product, productStock, productList)))))
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

  private ProductRequest setProductPrice(Product product, ProductStock productStock, ProductRequest productRequest) {

    if (productRequest.getPriceType() == PriceType.PRODUCT) {
      productRequest.setPrice(productStock.getGroceryPrice());
      productRequest.setTotalPrice(productStock.getGroceryPrice() * productRequest.getQty());
    } else {
      productRequest.setPrice(productRequest.getPrice());
      productRequest.setTotalPrice(productRequest.getPrice() * productRequest.getQty());
    }
    productRequest.setProductName(product.getName());
    productRequest.setUnitOfMeasure(product.getUnitOfMeasure());

    return productRequest;
  }

  private PurchaseRequest updatePR(List<ProductRequest> productRequests,
      UpdatePurchaseRequestByIdCommandRequest request, PurchaseRequest purchaseRequest) {
    purchaseRequest.setProductList(productRequests);
    purchaseRequest.setNote(request.getNote());
    return purchaseRequest;
  }
}
