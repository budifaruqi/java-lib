package com.example.test.web.model.response.purchaseRequest;

import com.example.test.common.enums.PRStatus;
import com.example.test.common.vo.PartnerVO;
import com.example.test.common.vo.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPurchaseRequestWebResponse {

  private String id;

  private String customerId;

  private String vendorId;

  private PartnerVO customer;

  private PartnerVO vendor;

  private List<ProductRequest> productList;

  private Long amountTotal;

  private PRStatus status;

  private String note;
}
