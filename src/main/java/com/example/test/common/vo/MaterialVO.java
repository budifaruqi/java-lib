package com.example.test.common.vo;

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
public class MaterialVO {

  @NotBlank
  private String productId;

  private String productName;

  @NotNull
  @Min(value = 0, message = ErrorCode.MIN_VALUE_IS_0)
  private Long qty;

  private String unitOfMeasure;
}
