package com.example.test.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerVO {

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
}
