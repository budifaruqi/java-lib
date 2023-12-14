package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.TransactionScope;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.common.enums.TransactionType;
import com.example.test.common.vo.ProductRequest;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = CollectionName.TRANSACTION)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends BaseEntity {

  @Id
  private String id;

  private String transactionId;

  private String purchaseRequestId;

  private String bomProductionId;

  private List<ProductRequest> productList;

  private TransactionScope transactionScope;

  private TransactionType transactionType;

  private TransactionStatus transactionStatus;

  private List<String> serialNumberList;

  private Long amountTotal;

  private Long amountPpn;

  private Date deliveredDate;
}
