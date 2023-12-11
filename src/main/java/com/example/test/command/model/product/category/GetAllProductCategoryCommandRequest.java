package com.example.test.command.model.product.category;

import com.example.test.common.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllProductCategoryCommandRequest {

  private String name;

  private ProductType type;

  private Boolean isDevice;

  private Pageable pageable;
}
