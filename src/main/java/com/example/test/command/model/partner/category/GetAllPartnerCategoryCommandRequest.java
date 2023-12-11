package com.example.test.command.model.partner.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllPartnerCategoryCommandRequest {

  private String name;

  private Pageable pageable;
}
