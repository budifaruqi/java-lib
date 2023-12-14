package com.example.test.command.model.purchaseRequest;

import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PRStatus;
import com.example.test.validation.annotation.PRStatusSubset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePurchaseRequestStatusByIdCommandRequest {

  private String id;

  @NotNull(message = ErrorCode.STATUS_NOT_VALID)
  @PRStatusSubset(anyOf = {PRStatus.APPROVED, PRStatus.REJECTED, PRStatus.CANCELED})
  private PRStatus status;
}
