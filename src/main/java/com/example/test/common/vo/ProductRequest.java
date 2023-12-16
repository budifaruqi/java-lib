package com.example.test.common.vo;

import com.example.test.common.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

  @NotBlank
  private String productId;

  @NotNull
  private Long qty;

  private Long price;

  private Long totalPrice;

  private String productName;

  private String unitOfMeasure;

  @NotNull
  private PriceType priceType;
}
