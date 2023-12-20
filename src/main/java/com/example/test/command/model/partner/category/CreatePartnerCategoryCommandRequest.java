package com.example.test.command.model.partner.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePartnerCategoryCommandRequest {

  @NotBlank
  private String name;

  @NotNull
  private Boolean isPoint;

  @NotNull
  private Boolean isSPK;
}
