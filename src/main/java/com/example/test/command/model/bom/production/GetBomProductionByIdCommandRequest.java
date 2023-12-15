package com.example.test.command.model.bom.production;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBomProductionByIdCommandRequest {

  @NotBlank
  private String companyId;

  private String id;
}
