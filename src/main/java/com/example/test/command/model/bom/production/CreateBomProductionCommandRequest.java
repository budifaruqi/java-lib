package com.example.test.command.model.bom.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
