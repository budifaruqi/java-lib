package com.example.test.command.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductCommandRequest {

  @NotBlank
  private String id;

  @NotBlank
  private String categoryId;

  @NotBlank
  private String brandId;

  @NotBlank
  private String name;

  @NotBlank
  private String code;

  @NotBlank
  private String sku;

  @NotBlank
  private String unitOfMeasure;

  @NotNull
  private Boolean isActive;

  private String description;

  private List<String> imageUrls;
}
