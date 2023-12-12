package com.example.test.web.model.request.product.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductStockWebRequest {

  private String productId;

  private Long stock;

  private Long hpp;

  private Long retailPrice;

  private Long groceryPrice;
}
