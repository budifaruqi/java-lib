package com.example.test.command.model.bom.production;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBomProductionByIdCommandRequest {

  @NotBlank
  private String companyId;

  private String id;
}
