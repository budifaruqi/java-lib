package com.example.test.command.impl.transaction;

import com.example.test.command.model.transaction.ConfirmTransactionByIdCommandRequest;
import com.example.test.command.transaction.ConfirmTransactionByIdCommand;
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

import java.util.List;

@Service
public class ConfirmTransactionByIdCommandImpl implements ConfirmTransactionByIdCommand {

  private final TransactionRepository transactionRepository;

  private final PurchaseRequestRepository purchaseRequestRepository;

  private final PartnerRepository partnerRepository;

  private final ProductStockRepository productStockRepository;

  public ConfirmTransactionByIdCommandImpl(TransactionRepository transactionRepository,
      PurchaseRequestRepository purchaseRequestRepository, PartnerRepository partnerRepository,
      ProductStockRepository productStockRepository) {
    this.transactionRepository = transactionRepository;
    this.purchaseRequestRepository = purchaseRequestRepository;
    this.partnerRepository = partnerRepository;
    this.productStockRepository = productStockRepository;
  }

  @Override
  public Mono<GetTransactionWebResponse> execute(ConfirmTransactionByIdCommandRequest request) {
    return Mono.defer(() -> findTransaction(request.getId()))
        .filter(s -> s.getStatus() == TransactionStatus.DELIVERED && (s.getTransactionType() == TransactionType.BUY))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)))
        .flatMap(transaction -> Mono.defer(() -> findCustomer(transaction))
            .flatMap(customer -> Mono.fromSupplier(() -> updateStatus(transaction))
                .flatMap(transactionRepository::save)
                .flatMap(updatedTransaction -> Mono.fromSupplier(() -> updatedTransaction)
                    .filter(s -> s.getTransactionType() == TransactionType.BUY)
                    .flatMap(this::updatePR)
                    .flatMap(s -> updateStock(updatedTransaction, customer))
                    .map(s -> updatedTransaction)
                    .switchIfEmpty(Mono.fromSupplier(() -> updatedTransaction)))
                .map(this::toGetWebResponse)));
  }

  private Mono<MainTransaction> findTransaction(String request) {
    return transactionRepository.findByDeletedFalseAndId(request)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.TRANSACTION_NOT_EXIST)));
  }

  private Mono<Partner> findCustomer(MainTransaction mainTransaction) {
    return partnerRepository.findByDeletedFalseAndId(mainTransaction.getCustomerId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PARTNER_NOT_EXIST)));
  }

  private MainTransaction updateStatus(MainTransaction mainTransaction) {
    mainTransaction.setStatus(TransactionStatus.CONFIRMED);

    return mainTransaction;
  }

  private Mono<PurchaseRequest> updatePR(MainTransaction mainTransaction) {
    return Mono.defer(() -> findPR(mainTransaction))
        .flatMap(purchaseRequest -> Mono.fromSupplier(() -> purchaseRequest)
            .flatMap(purchaseRequest1 -> Flux.fromIterable(purchaseRequest.getTransactionIds())
                .flatMapSequential(this::findTransaction)
                .collectList()
                .map(this::checkStatus)
                .map(status -> toPR(status, purchaseRequest))
                .flatMap(purchaseRequestRepository::save)));
  }

  private boolean checkStatus(List<MainTransaction> transactionList) {
    boolean status = transactionList.stream()
        .allMatch(s -> s.getStatus() == TransactionStatus.CONFIRMED);
    System.out.println(status);

    return status;
  }

  private PurchaseRequest toPR(Boolean status, PurchaseRequest purchaseRequest) {
    purchaseRequest.setStatus(status ? PRStatus.COMPLETED : PRStatus.PARTIAL);
    return purchaseRequest;
  }

  private Mono<List<ProductStock>> updateStock(MainTransaction updatedTransaction, Partner customer) {
    return Flux.fromIterable(updatedTransaction.getProductList())
        .flatMap(productRequest -> Mono.defer(() -> findStock(productRequest, customer))
            .map(productStock -> updateStock(productRequest, productStock))
            .flatMap(productStockRepository::save))
        .collectList();
  }

  private Mono<ProductStock> findStock(ProductRequest productRequest, Partner customer) {
    return productStockRepository.findByDeletedFalseAndCompanyIdAndProductId(customer.getCompanyId(),
            productRequest.getProductId())
        .switchIfEmpty(Mono.fromSupplier(() -> ProductStock.builder()
            .productId(productRequest.getProductId())
            .companyId(customer.getCompanyId())
            .build()));
  }

  private ProductStock updateStock(ProductRequest productRequest, ProductStock productStock) {
    productStock.setStock(productRequest.getQty());
    productStock.setHpp(productRequest.getPrice());

    return productStock;
  }

  private Mono<PurchaseRequest> findPR(MainTransaction mainTransaction) {
    return purchaseRequestRepository.findByDeletedFalseAndVendorIdAndId(mainTransaction.getVendorId(),
            mainTransaction.getPurchaseRequestId())
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.PURCHASE_REQUEST_NOT_EXIST)))
        .filter(s -> s.getStatus() == PRStatus.APPROVED || s.getStatus() == PRStatus.PARTIAL)
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.STATUS_NOT_VALID)))
        .filter(purchaseRequest -> purchaseRequest.getTransactionIds()
            .contains(mainTransaction.getId()))
        .switchIfEmpty(Mono.error(new ValidationException(ErrorCode.TRANSACTION_ID_NOT_IN_PURCHASE_REQUEST)));
  }

  private GetTransactionWebResponse toGetWebResponse(MainTransaction s) {
    return GetTransactionWebResponse.builder()
        .id(s.getId())
        .vendorId(s.getVendorId())
        .customerId(s.getCustomerId())
        .purchaseRequestId(s.getPurchaseRequestId())
        .bomProductionId(s.getBomProductionId())
        .productList(s.getProductList())
        .transactionScope(s.getTransactionScope())
        .transactionType(s.getTransactionType())
        .amountPpn(s.getAmountPpn())
        .amountTotal(s.getAmountTotal())
        .deliveredDate(s.getDeliveredDate())
        .status(s.getStatus())
        .build();
  }
}
