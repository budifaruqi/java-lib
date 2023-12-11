package com.example.test.command.model.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllPartnerCommandRequest {

  private String name;

  private String categoryId;

  private Boolean isVendor;

  private Boolean isCustomer;

  private Pageable pageable;
}
