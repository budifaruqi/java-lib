package com.example.test.web.model.response.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPartnerWebResponse {

  private String id;

  private String categoryId;

  private String name;

  private String phone;

  private String email;

  private String address;

  private String picName;

  private String picPhone;

  private String picEmail;

  private Boolean isVendor;

  private Boolean isCustomer;

  private Boolean isInternal;

  private String companyId;
}
