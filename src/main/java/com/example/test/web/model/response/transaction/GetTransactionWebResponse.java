package com.example.test.web.model.response.transaction;

import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTransactionWebResponse {

  private String id;

  private String vendorId;

  private String customerId;

  private String purchaseRequestId;

  private String bomProductionId;

  private List<ProductRequest> productList;

  private TransactionScope transactionScope;

  private TransactionType transactionType;
  
  private Long amountTotal;

  private Long amountPpn;

  private Date deliveredDate;

  private TransactionStatus status;
}
