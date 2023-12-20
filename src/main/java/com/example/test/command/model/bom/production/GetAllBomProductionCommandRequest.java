package com.example.test.command.model.bom.production;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllBomProductionCommandRequest {

  @NotBlank
  private String companyId;

  private String transactionId;

  private Date startDate;

  private Date endDate;

  private Pageable pageable;
}
