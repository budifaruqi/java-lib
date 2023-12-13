package com.example.test.command.model.product.stock;

import com.example.test.common.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductStockByIdCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String id;

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
