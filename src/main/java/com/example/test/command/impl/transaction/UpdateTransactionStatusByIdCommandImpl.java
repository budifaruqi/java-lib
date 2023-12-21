package com.example.test.command.impl.transaction;

import com.example.test.command.model.transaction.UpdateTransactionStatusByIdCommandRequest;
import com.example.test.command.transaction.UpdateTransactionStatusByIdCommand;
import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.repository.PartnerRepository;
import com.example.test.repository.ProductStockRepository;
import com.example.test.repository.TransactionRepository;
import com.example.test.repository.model.MainTransaction;
import com.example.test.repository.model.Partner;
import com.example.test.web.model.response.transaction.GetTransactionWebResponse;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateTransactionStatusByIdCommandImpl implements UpdateTransactionStatusByIdCommand {

  private final TransactionRepository transactionRepository;

  private final ProductStockRepository productStockRepository;

  private final PartnerRepository partnerRepository;

  public UpdateTransactionStatusByIdCommandImpl(TransactionRepository transactionRepository,
      ProductStockRepository productStockRepository, PartnerRepository partnerRepository) {
    this.transactionRepository = transactionRepository;
    this.productStockRepository = productStockRepository;
    this.partnerRepository = partnerRepository;
  }

  @Override
  public Mono<GetTransactionWebResponse> execute(UpdateTransactionStatusByIdCommandRequest request) {
    return Mono.defer(() -> findTransaction(request))
        .flatMap(transaction -> Mono.defer(() -> getVendor(transaction))
            .map(vendor -> updateStatus(transaction, request))
            .flatMap(updatedTransaction -> Mono.fromSupplier(() -> updatedTransaction)
                .filter(updatedTransaction1 -> updatedTransaction1.getStatus() == TransactionStatus.CANCELED))
            .flatMap(transactionRepository::save)
            .map(this::toGetWebResponse));
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
        .filter(transaction -> transaction.getTransactionStatus() == TransactionStatus.PROCESSED)
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
    mainTransaction.setTransactionStatus(request.getStatus());

    return mainTransaction;
  }
}
