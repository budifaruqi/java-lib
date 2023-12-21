package com.example.test.command.model.partner.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllPartnerTagCommandRequest {

  private String name;

  private Pageable pageable;
}
