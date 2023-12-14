package com.example.test.command.model.purchaseRequest;

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
public class UpdatePurchaseRequestByIdCommandRequest {

  private String id;

  private List<ProductRequest> productList;

  private String note;
}
