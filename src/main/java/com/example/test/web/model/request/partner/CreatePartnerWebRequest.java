package com.example.test.web.model.request.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePartnerWebRequest {

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

  private Boolean isInternal;

  private String companyId;

  private List<String> partnerTagIds;
}
