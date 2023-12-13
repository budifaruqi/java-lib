package com.example.test.web.model.request.purchaseRequest;

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
public class CreatePurchaseRequestWebRequest {

  private String vendorId;

  private List<ProductRequest> productList;

  private String note;
}
