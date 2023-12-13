package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.example.test.common.enums.PRStatus;
import com.example.test.common.vo.ProductRequest;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CollectionName.PURCHASE_REQUEST)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest extends BaseEntity {

  @Id
  private String id;

  private String customerId;

  private String vendorId;

  private List<ProductRequest> productList;

  private Long amountTotal;

  private PRStatus status;

  private String note;
}
