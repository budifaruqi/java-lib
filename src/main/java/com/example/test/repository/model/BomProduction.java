package com.example.test.repository.model;

import com.example.test.common.constant.CollectionName;
import com.solusinegeri.data.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CollectionName.BOM_PRODUCTION)
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BomProduction extends BaseEntity {

  @Id
  private String id;

  private String companyId;

  private String transactionId;

  private String bomId;

  private Long qty;

  private Long amount;

  private Long amountTotal;
}
