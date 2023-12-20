package com.example.test.command.model.product.stock;

import com.example.test.common.constant.ErrorCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductStockCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String productId;

  @NotNull
  @Min(value = 1, message = ErrorCode.MIN_VALUE_IS_0)
  private Long stock;

  @NotNull
  @Min(value = 1, message = ErrorCode.MIN_VALUE_IS_0)
  private Long hpp;

  @NotNull
  @Min(value = 1, message = ErrorCode.MIN_VALUE_IS_0)
  private Long retailPrice;

  @NotNull
  @Min(value = 1, message = ErrorCode.MIN_VALUE_IS_0)
  private Long groceryPrice;
}
