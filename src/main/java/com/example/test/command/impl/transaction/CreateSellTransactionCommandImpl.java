package com.example.test.command.impl.transaction;

import com.example.test.command.model.transaction.CreateSellTransactionCommandRequest;
import com.example.test.command.transaction.CreateSellTransactionCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PriceType;
import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.MainTransaction;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.Product;
import com.example.test.repository.model.ProductStock;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CreateSellTransactionCommandImpl implements CreateSellTransactionCommand {

  private final TransactionRepository transactionRepository;

  private final PartnerRepository partnerRepository;

  private final ProductStockRepository productStockRepository;

  private final ProductRepository productRepository;

  public CreateSellTransactionCommandImpl(TransactionRepository transactionRepository,
      PartnerRepository partnerRepository, ProductStockRepository productStockRepository,
      ProductRepository productRepository) {
    this.transactionRepository = transactionRepository;
    this.partnerRepository = partnerRepository;
    this.productStockRepository = productStockRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Object> execute(CreateSellTransactionCommandRequest request) {
    return Mono.zip(checkVendor(request), getCustomer(request))
        .flatMap(objects -> Mono.defer(() -> buildProduct(request, objects.getT1()))
            .map(productRequests -> toTransaction(request, productRequests, objects.getT1(), objects.getT2())));
  }

  private Mono<Partner> getCustomer(CreateSellTransactionCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndId(request.getCustomerId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsCustomer)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_CUSTOMER)));
  }

  private Mono<Partner> checkVendor(CreateSellTransactionCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndId(request.getCustomerId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsVendor)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_VENDOR)));
  }

  private Mono<List<ProductRequest>> buildProduct(CreateSellTransactionCommandRequest request, Partner vendor) {
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

    return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(vendor.getCompanyId(),
            productRequest.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)))
        .filter(productStock -> productStock.getStock() >= productRequest.getQty())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH)))
        .map(productStock -> updateStock(productStock, productRequest))
        .flatMap(productStockRepository::save);
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
    //    productRequest.setProcessedQty(0L);

    return productRequest;
  }

  private Long getAmountProduct(List<ProductRequest> productRequestList) {
    return productRequestList.stream()
        .mapToLong(ProductRequest::getTotalPrice)
        .sum();
  }

  private MainTransaction toTransaction(CreateSellTransactionCommandRequest request,
      List<ProductRequest> productRequests, Partner vendor, Partner customer) {
    return MainTransaction.builder()
        .vendorId(vendor.getId())
        .customerId(customer.getId())
        .productList(productRequests)
        .transactionScope(customer.getIsInternal() ? TransactionScope.INTERNAL : TransactionScope.EXTERNAL)
        .transactionType(TransactionType.SELL)
        .amountTotal(getAmountProduct(productRequests))
        .status(TransactionStatus.PROCESSED)
        .build();
  }
}
