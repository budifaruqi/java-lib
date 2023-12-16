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
public class CreateTransactionWebRequest {

  private String purchaseRequestId;

  private List<ProductRequest> productList;
}
