package com.example.test.command.model.product.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductStockCommandRequest {

  private String companyId;

  private String productId;

  private Pageable pageable;
}
