package com.example.test.web.model.response.product.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductStockWebResponse {

  @Id
  private String id;

  private String productId;

  private String companyId;

  private Long stock;

  private Long hpp;

  private Long retailPrice;

  private Long groceryPrice;
}
