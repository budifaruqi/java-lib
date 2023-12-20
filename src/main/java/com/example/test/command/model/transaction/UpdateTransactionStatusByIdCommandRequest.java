package com.example.test.command.model.transaction;

import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.validation.annotation.TransactionStatusSubset;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionStatusByIdCommandRequest {

  private String id;

  @NotNull(message = ErrorCode.STATUS_NOT_VALID)
  @TransactionStatusSubset(anyOf = {TransactionStatus.DELIVERED, TransactionStatus.CANCELED})
  private TransactionStatus status;
}
