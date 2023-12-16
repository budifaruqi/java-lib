package com.example.test.command.impl.transaction;

import com.example.test.command.model.transaction.CreateTransactionCommandRequest;
import com.example.test.command.transaction.CreateTransactionCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PurchaseRequest;
import com.example.test.repository.model.Transaction;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreateTransactionCommandImpl implements CreateTransactionCommand {

  private final TransactionRepository transactionRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  private final ProductStockRepository productStockRepository;

  private final PartnerRepository partnerRepository;

  public CreateTransactionCommandImpl(TransactionRepository transactionRepository,
      PurchaseRequestRepository purchaseRequestRepository, ProductStockRepository productStockRepository,
      PartnerRepository partnerRepository) {
    this.transactionRepository = transactionRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.productStockRepository = productStockRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Object> execute(CreateTransactionCommandRequest request) {
    return Mono.fromSupplier(this::generateTransactionId)
        .flatMap(transactionId -> Mono.defer(() -> findPR(request))
            .flatMap(purchaseRequest -> Mono.defer(() -> getCustomer(purchaseRequest))
                .flatMap(customer -> Mono.defer(() -> checkProduct(purchaseRequest, request))
                    .map(productList -> toTransaction(transactionId, purchaseRequest, productList, customer)))));
  }

  private Mono<Partner> getCustomer(PurchaseRequest purchaseRequest) {
    return partnerRepository.findByDeletedFalseAndId(purchaseRequest.getCustomerId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }

  private Transaction toTransaction(String transactionId, PurchaseRequest purchaseRequest,
      List<ProductRequest> productList, Partner customer) {
    return Transaction.builder()
        .transactionId(transactionId)
        .vendorId(purchaseRequest.getVendorId())
        .customerId(purchaseRequest.getCustomerId())
        .purchaseRequestId(purchaseRequest.getId())
        .productList(productList)
        .transactionScope(customer.getIsInternal() ? TransactionScope.INTERNAL : TransactionScope.EXTERNAL)
        .transactionType(TransactionType.SELL)
        .build();
  }

  //  private Long getAmountProduct(List<ProductRequest> productList)

  private String generateTransactionId() {
    return String.valueOf(new ObjectId());
  }

  private Mono<PurchaseRequest> findPR(CreateTransactionCommandRequest request) {
    return purchaseRequestRepository.findByDeletedFalseAndAndVendorIdAndId(request.getCompanyId(),
            request.getPurchaseRequestId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(purchaseRequest -> purchaseRequest.getStatus() == PRStatus.APPROVED ||
            purchaseRequest.getStatus() == PRStatus.PARTIAL)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_STATUS_NOT_VALID)));
  }

  private Mono<List<ProductRequest>> checkProduct(PurchaseRequest purchaseRequest,
      CreateTransactionCommandRequest request) {

    List<ProductRequest> matchingProducts = new ArrayList<>();

    for (ProductRequest pr : request.getProductList()) {
      // Find the corresponding product in the purchaseRequest based on productId
      Optional<ProductRequest> matchingProduct = purchaseRequest.getProductList()
          .stream()
          .filter(product -> product.getProductId()
              .equals(pr.getProductId()))
          .findFirst();
      System.out.println("X");
      System.out.println(matchingProduct);

      // Check if the product is present in both lists
      if (matchingProduct.isPresent()) {
        // Compare quantities (allowing less or equal qty in the productList)
        if (pr.getQty() > matchingProduct.get()
            .getQty()) {
          return Mono.error(new ValidationException(ErrorCode.QUANTITY_MISMATCH));
        }

        // Set fields from the purchaseRequest to the matching product
        pr.setPrice(matchingProduct.get()
            .getPrice());
        pr.setTotalPrice(matchingProduct.get()
            .getTotalPrice());
        pr.setProductName(matchingProduct.get()
            .getProductName());
        pr.setUnitOfMeasure(matchingProduct.get()
            .getUnitOfMeasure());

        // Add matching product to the result list
        matchingProducts.add(pr);
        System.out.println(matchingProduct);
      } else {
        return Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_FOUND));
      }
    }
    // All products matched successfully, return the result list
    return Mono.fromSupplier(() -> matchingProducts);
  }
}
