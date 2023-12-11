package com.example.test.command.model.partner.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePartnerCategoryCommandRequest {

  @NotBlank
  private String id;

  @NotBlank
  private String name;

  @NotNull
  private Boolean isPoint;

  @NotNull
  private Boolean isSPK;
}
