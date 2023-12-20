package com.example.test.command.model.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductCommandRequest {

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

  private String description;

  private List<String> imageUrls;

  private List<String> companyShare;
}
