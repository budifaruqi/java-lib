package com.example.test.common.vo;

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
public class MaterialVO {

  @NotBlank
  private String productId;

  private String productName;

  @NotNull
  @Min(value = 0, message = ErrorCode.MIN_VALUE_IS_0)
  private Double qty;

  private String unitOfMeasure;
}
