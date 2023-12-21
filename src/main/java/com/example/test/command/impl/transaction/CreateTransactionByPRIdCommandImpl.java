package com.example.test.command.impl.transaction;

import com.example.test.command.model.transaction.CreateTransactionByPRIdCommandRequest;
import com.example.test.command.transaction.CreateTransactionByPRIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.MainTransaction;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.PurchaseRequest;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreateTransactionByPRIdCommandImpl implements CreateTransactionByPRIdCommand {

  private final TransactionRepository transactionRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  private final ProductStockRepository productStockRepository;

  private final PartnerRepository partnerRepository;

  public CreateTransactionByPRIdCommandImpl(TransactionRepository transactionRepository,
      PurchaseRequestRepository purchaseRequestRepository, ProductStockRepository productStockRepository,
      PartnerRepository partnerRepository) {
    this.transactionRepository = transactionRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.productStockRepository = productStockRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<Object> execute(CreateTransactionByPRIdCommandRequest request) {
    return Mono.defer(() -> getVendor(request))
        .flatMap(vendor -> Mono.defer(() -> findPR(request, vendor))
            .flatMap(purchaseRequest -> Mono.defer(() -> getCustomer(purchaseRequest))
                .flatMap(customer -> Mono.defer(() -> checkProduct(purchaseRequest, request))
                    .map(productList -> toTransaction(purchaseRequest, productList, customer))
                    .flatMap(transactionRepository::save))));
  }

  private String generateTransactionId() {
    return String.valueOf(new ObjectId());
  }

  private Mono<PurchaseRequest> findPR(CreateTransactionByPRIdCommandRequest request, Partner vendor) {
    return purchaseRequestRepository.findByDeletedFalseAndVendorIdAndId(vendor.getId(), request.getPurchaseRequestId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(purchaseRequest -> purchaseRequest.getStatus() == PRStatus.APPROVED ||
            purchaseRequest.getStatus() == PRStatus.PARTIAL)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_STATUS_NOT_VALID)));
  }

  private Mono<Partner> getCustomer(PurchaseRequest purchaseRequest) {
    return partnerRepository.findByDeletedFalseAndId(purchaseRequest.getCustomerId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsCustomer)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_CUSTOMER)));
  }

  private Mono<Partner> getVendor(CreateTransactionByPRIdCommandRequest request) {
    return partnerRepository.findByDeletedFalseAndCompanyId(request.getCompanyId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsVendor)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_VENDOR)));
  }

  private Long getAmountProduct(List<ProductRequest> productRequestList) {
    return productRequestList.stream()
        .mapToLong(ProductRequest::getTotalPrice)
        .sum();
  }

  private Mono<List<ProductRequest>> checkProduct(PurchaseRequest purchaseRequest,
      CreateTransactionByPRIdCommandRequest request) {

    List<ProductRequest> matchingProducts = new ArrayList<>();
    //    boolean hasPartialQuantity = false;

    for (ProductRequest pr : request.getProductList()) {
      // Find the corresponding product in the purchaseRequest based on productId
      Optional<ProductRequest> matchingProduct = purchaseRequest.getProductList()
          .stream()
          .filter(product -> product.getProductId()
              .equals(pr.getProductId()))
          .findFirst();

      // Check if the product is present in both lists
      if (matchingProduct.isPresent()) {
        // Compare quantities (allowing less or equal qty in the productList)
        if (pr.getQty() > matchingProduct.get()
            .getQty()) {
          return Mono.error(new ValidationException(ErrorCode.QUANTITY_MISMATCH,
              "Quantity mismatch for productId: " + pr.getProductId()));
        }
        //        else if (pr.getQty() < matchingProduct.get()
        //            .getQty() && matchingProduct.get()
        //            .getProcessedQty() < pr.getQty()) {
        //          // If pr.qty is less, set the flag to true and set PRStatus to PARTIAL
        //          hasPartialQuantity = true;
        //          purchaseRequest.setStatus(PRStatus.PARTIAL);
        //        }
        long newProcessedQty = matchingProduct.get()
            .getProcessedQty() + pr.getQty();
        if (newProcessedQty > matchingProduct.get()
            .getQty()) {
          return Mono.error(new ValidationException(ErrorCode.PROCESSED_QTY_EXCEEDS_REQUESTED_QTY,
              "Processed quantity exceeds requested quantity for productId: " + pr.getProductId()));
        }

        matchingProduct.get()
            .setProcessedQty(newProcessedQty);
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
      } else {
        return Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_FOUND));
      }
    }
    //    if (!hasPartialQuantity) {
    //      // If no partial quantity, set PRStatus to COMPLETED
    //      purchaseRequest.setStatus(PRStatus.COMPLETED);
    //    }
    // All products matched successfully, return the result list
    return Mono.defer(() -> purchaseRequestRepository.save(purchaseRequest))
        .map(s -> matchingProducts);
  }

  private MainTransaction toTransaction(PurchaseRequest purchaseRequest, List<ProductRequest> productList,
      Partner customer) {
    return MainTransaction.builder()
        .vendorId(purchaseRequest.getVendorId())
        .customerId(purchaseRequest.getCustomerId())
        .purchaseRequestId(purchaseRequest.getId())
        .productList(productList)
        .transactionScope(customer.getIsInternal() ? TransactionScope.INTERNAL : TransactionScope.EXTERNAL)
        .transactionType(TransactionType.BUY)
        .amountTotal(getAmountProduct(productList))
        .status(TransactionStatus.PROCESSED)
        .build();
  }
}
