package com.example.test.command.model.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBrandCommandRequest {

  @NotBlank
  private String id;

  @NotBlank
  private String name;
}
