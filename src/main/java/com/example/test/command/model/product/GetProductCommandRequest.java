package com.example.test.command.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductCommandRequest {

  private String categoryId;

  private String brandId;

  private String name;

  private String code;

  private String sku;

  private Pageable pageable;
}
