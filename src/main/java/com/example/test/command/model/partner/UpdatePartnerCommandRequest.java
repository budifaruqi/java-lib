package com.example.test.command.model.partner;

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
public class UpdatePartnerCommandRequest {

  @NotBlank
  private String id;

  @NotBlank
  private String name;

  @NotBlank
  private String categoryId;

  @NotBlank
  private String phone;

  private String email;

  @NotBlank
  private String address;

  @NotBlank
  private String picName;

  @NotBlank
  private String picPhone;

  private String picEmail;

  @NotNull
  private Boolean isVendor;

  @NotNull
  private Boolean isCustomer;
}