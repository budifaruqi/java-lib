package com.example.test.web.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductWebRequest {

  private String categoryId;

  private String brandId;

  private String name;

  private String code;

  private String sku;

  private String unitOfMeasure;

  private String description;

  private List<String> imageUrls;

  private List<String> companyShare;
}
