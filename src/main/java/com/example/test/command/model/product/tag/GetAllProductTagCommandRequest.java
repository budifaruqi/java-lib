package com.example.test.command.model.product.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllProductTagCommandRequest {

  private String name;

  private Pageable pageable;
}
