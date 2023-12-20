package com.example.test.command.impl.purchaseRequest;

import com.example.test.command.model.purchaseRequest.CreatePurchaseRequestCommandRequest;
import com.example.test.command.purchaseRequest.CreatePurchaseRequestCommand;
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
public class CreatePurchaseRequestCommandImpl implements CreatePurchaseRequestCommand {

  private final ProductRepository productRepository;

  private final ProductStockRepository productStockRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  private final PartnerRepository partnerRepository;

  public CreatePurchaseRequestCommandImpl(ProductRepository productRepository,
      ProductStockRepository productStockRepository, PurchaseRequestRepository purchaseRequestRepository,
      PartnerRepository partnerRepository) {
    this.productRepository = productRepository;
    this.productStockRepository = productStockRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Object> execute(CreatePurchaseRequestCommandRequest request) {
    return Mono.defer(() -> checkPartner(request))
        .flatMap(vendor -> Mono.defer(() -> buildProduct(request, vendor))
            .map(productList -> toPurchaseRequest(productList, request))
            .flatMap(purchaseRequestRepository::save));
  }

  private Mono<Partner> checkPartner(CreatePurchaseRequestCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndId(request.getVendorId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsVendor)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_VENDOR)));
  }

  private Mono<List<ProductRequest>> buildProduct(CreatePurchaseRequestCommandRequest request, Partner vendor) {
    return Flux.fromIterable(request.getProductList())
        .flatMapSequential(this::validateProduct)
        .flatMap(productRequest -> Mono.defer(() -> findProduct(productRequest))
            .flatMap(product -> Mono.defer(() -> findProductStock(vendor, productRequest))
                .map(productStock -> setProductPrice(product, productStock, productRequest))))
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
    productRequest.setProcessedQty(0L);

    return productRequest;
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

