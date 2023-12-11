package com.example.test.web.model.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductWebResponse {

  private String id;

  private String categoryId;

  private String categoryName;

  private String brandId;

  private String brandName;

  private String name;

  private String code;

  private String sku;

  private String unitOfMeasure;

  private Boolean isActive;

  private String description;

  private List<String> imageUrls;
}
