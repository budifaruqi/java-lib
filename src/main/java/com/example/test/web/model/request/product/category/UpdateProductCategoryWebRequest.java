package com.example.test.web.model.request.product.category;

import com.example.test.common.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductCategoryWebRequest {

  private String name;

  private ProductType type;

  private Boolean isDevice;

  private Long achievementPoint;

  private Long memberPoint;
}
