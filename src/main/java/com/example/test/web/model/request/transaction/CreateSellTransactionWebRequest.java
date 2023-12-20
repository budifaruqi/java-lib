package com.example.test.web.model.request.transaction;

import com.example.test.common.vo.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSellTransactionWebRequest {

  private String companyId;

  private List<ProductRequest> productList;

  private String customerId;

  private String note;
}
