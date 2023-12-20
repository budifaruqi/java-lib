package com.example.test.common.vo;

import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.PriceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

  @NotBlank
  private String productId;

  @NotNull
  @Min(value = 1, message = ErrorCode.MIN_VALUE_IS_1)
  private Long qty;

  private Long processedQty;

  private Long price;

  private Long totalPrice;

  private String productName;

  private String unitOfMeasure;

  @NotNull
  private PriceType priceType;

  private List<String> serialNumberList;
}
