package com.example.test.command.model.bom.production;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBomProductionCommandRequest {

  @NotBlank
  private String companyId;

  @NotBlank
  private String bomId;

  @NotNull
  private Long qty;
}
