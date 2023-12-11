package com.example.test.web.model.request.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePartnerWebRequest {

  private String name;

  private String categoryId;

  private String phone;

  private String email;

  private String address;

  private String picName;

  private String picPhone;

  private String picEmail;

  private Boolean isVendor;

  private Boolean isCustomer;
}
