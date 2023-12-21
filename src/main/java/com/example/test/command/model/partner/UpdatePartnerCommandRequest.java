package com.example.test.command.model.partner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

  private List<String> partnerTagIds;
}
