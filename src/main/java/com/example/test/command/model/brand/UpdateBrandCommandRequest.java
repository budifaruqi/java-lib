package com.example.test.command.model.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
