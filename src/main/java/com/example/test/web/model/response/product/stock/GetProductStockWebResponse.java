package com.example.test.web.model.response.product.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductStockWebResponse {

  private String id;

  private String productId;

  private String productName;

  private String companyId;

  private Long stock;

  private String unitOfMeasure;

  private Long hpp;

  private Long retailPrice;

  private Long groceryPrice;
}
