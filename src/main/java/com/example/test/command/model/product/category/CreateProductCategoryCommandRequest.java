package com.example.test.command.model.product.category;

import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductCategoryCommandRequest {

  @NotBlank
  private String name;

  @NotNull
  private ProductType type;

  @NotNull
  private Boolean isDevice;

  @NotNull
  @Min(value = 0, message = ErrorCode.MIN_VALUE_IS_0)
  @Max(value = 100, message = ErrorCode.MAX_VALUE_IS_100)
  private Long achievementPoint;

  @NotNull
  private Long memberPoint;
}