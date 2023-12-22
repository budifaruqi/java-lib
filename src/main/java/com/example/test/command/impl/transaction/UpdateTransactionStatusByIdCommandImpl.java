package com.example.test.command.impl.transaction;

import com.example.test.command.model.transaction.UpdateTransactionStatusByIdCommandRequest;
import com.example.test.command.transaction.UpdateTransactionStatusByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.PurchaseRequestRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.MainTransaction;
import com.example.test.repository.model.Partner;
import com.example.test.repository.model.ProductStock;
import com.example.test.repository.model.PurchaseRequest;
import com.example.test.web.model.response.transaction.GetTransactionWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UpdateTransactionStatusByIdCommandImpl implements UpdateTransactionStatusByIdCommand {

  private final TransactionRepository transactionRepository;

  private final ProductStockRepository productStockRepository;

  private final PartnerRepository partnerRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  public UpdateTransactionStatusByIdCommandImpl(TransactionRepository transactionRepository,
      ProductStockRepository productStockRepository, PartnerRepository partnerRepository,
      PurchaseRequestRepository purchaseRequestRepository) {
    this.transactionRepository = transactionRepository;
    this.productStockRepository = productStockRepository;
    this.partnerRepository = partnerRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
  }

  @Override
  public Mono<GetTransactionWebResponse> execute(UpdateTransactionStatusByIdCommandRequest request) {
    return Mono.defer(() -> findTransaction(request))
        .flatMap(transaction -> Mono.defer(() -> getVendor(transaction))
            .flatMap(vendor -> Mono.fromSupplier(() -> updateStatus(transaction, request))
                .flatMap(updatedTransaction -> Mono.fromSupplier(() -> updatedTransaction)
                    .filter(updatedTransaction1 -> updatedTransaction1.getStatus() == TransactionStatus.CANCELED &&
                        updatedTransaction1.getTransactionType() == TransactionType.BUY)
                    .flatMap(this::updatePR)
                    .flatMap(s -> Mono.fromSupplier(() -> updatedTransaction)
                        .filter(updatedTransaction1 -> updatedTransaction1.getStatus() == TransactionStatus.CANCELED &&
                            updatedTransaction1.getTransactionType() == TransactionType.SELL)
                        .flatMap(transaction1 -> updateStockVendor(transaction1, vendor)))
                    .switchIfEmpty(Mono.fromSupplier(() -> updatedTransaction)))
                .flatMap(transactionRepository::save)
                .map(this::toGetWebResponse)));
  }

  private Mono<MainTransaction> updateStockVendor(MainTransaction transaction, Partner vendor) {
    return Flux.fromIterable(transaction.getProductList())
        .flatMap(productRequest -> Mono.defer(() -> findStock(productRequest, vendor))
            .map(productStock -> updateStock(productRequest, productStock))
            .flatMap(productStockRepository::save))
        .collectList()
        .map(s -> transaction);
  }

  private Mono<ProductStock> findStock(ProductRequest productRequest, Partner vendor) {
    return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(vendor.getCompanyId(),
            productRequest.getProductId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PRODUCT_STOCK_NOT_EXIST)));
  }

  private ProductStock updateStock(ProductRequest productRequest, ProductStock productStock) {
    productStock.setStock(productStock.getStock() + productRequest.getQty());

    return productStock;
  }

  private Mono<MainTransaction> updatePR(MainTransaction mainTransaction) {
    return Mono.defer(() -> findPR(mainTransaction))
        .map(pr -> toPR(pr, mainTransaction))
        .flatMap(pr -> checkProduct(pr, mainTransaction))
        .map(s -> mainTransaction);
  }

  private Mono<List<ProductRequest>> checkProduct(PurchaseRequest purchaseRequest, MainTransaction mainTransaction) {
    List<ProductRequest> matchingProducts = new ArrayList<>();

    for (ProductRequest pr : mainTransaction.getProductList()) {
      // Find the corresponding product in the purchaseRequest based on productId
      Optional<ProductRequest> matchingProduct = purchaseRequest.getProductList()
          .stream()
          .filter(product -> product.getProductId()
              .equals(pr.getProductId()))
          .findFirst();

      // Check if the product is present in both lists
      if (matchingProduct.isPresent()) {
        // Compare quantities (allowing less or equal qty in the productList)

        long newProcessedQty = matchingProduct.get()
            .getProcessedQty() - pr.getQty();

        matchingProduct.get()
            .setProcessedQty(newProcessedQty);
        // Set fields from the purchaseRequest to the matching product

        // Add matching product to the result list
        matchingProducts.add(pr);
      } else {
        return Mono.error(new ValidationException(ErrorCode.PRODUCT_NOT_FOUND));
      }
    }

    // All products matched successfully, return the result list
    return Mono.defer(() -> purchaseRequestRepository.save(purchaseRequest))
        .map(s -> matchingProducts);
  }

  private PurchaseRequest toPR(PurchaseRequest pr, MainTransaction mainTransaction) {
    List<String> transactionIds = pr.getTransactionIds();

    // Remove the mainTransaction.id from the list, if present
    transactionIds.remove(mainTransaction.getId());

    // Assuming there's a method to set the updated list back in the PurchaseRequest
    pr.setTransactionIds(transactionIds);

    return pr;
  }

  private Mono<PurchaseRequest> findPR(MainTransaction mainTransaction) {
    return purchaseRequestRepository.findByDeletedFalseAndVendorIdAndId(mainTransaction.getVendorId(),
            mainTransaction.getPurchaseRequestId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(s -> s.getStatus() == PRStatus.APPROVED || s.getStatus() == PRStatus.PARTIAL)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_STATUS_NOT_VALID)));
  }

  private GetTransactionWebResponse toGetWebResponse(MainTransaction mainTransaction) {
    return GetTransactionWebResponse.builder()
        .id(mainTransaction.getId())
        .vendorId(mainTransaction.getVendorId())
        .customerId(mainTransaction.getCustomerId())
        .purchaseRequestId(mainTransaction.getPurchaseRequestId())
        .bomProductionId(mainTransaction.getBomProductionId())
        .productList(mainTransaction.getProductList())
        .transactionScope(mainTransaction.getTransactionScope())
        .transactionType(mainTransaction.getTransactionType())
        .status(mainTransaction.getStatus())
        .amountTotal(mainTransaction.getAmountTotal())
        .amountPpn(mainTransaction.getAmountPpn())
        .build();
  }

  private Mono<MainTransaction> findTransaction(UpdateTransactionStatusByIdCommandRequest request) {
    return transactionRepository.findByDeletedFalseAndId(request.getId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.TRANSACTION_NOT_EXIST)))
        .filter(transaction -> transaction.getStatus() == TransactionStatus.PROCESSED)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)));
  }

  private Mono<Partner> getVendor(MainTransaction mainTransaction) {
    return partnerRepository.findByDeletedFalseAndId(mainTransaction.getVendorId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)))
        .filter(Partner::getIsVendor)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_IS_NOT_A_VENDOR)));
  }

  private MainTransaction updateStatus(MainTransaction mainTransaction,
      UpdateTransactionStatusByIdCommandRequest request) {
    if (request.getStatus() == TransactionStatus.DELIVERED) {
      mainTransaction.setDeliveredDate(new Date());
    }
    mainTransaction.setStatus(request.getStatus());

    return mainTransaction;
  }
}
