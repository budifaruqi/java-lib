package com.example.test.common.enums;

public enum RoleEnum {
  ADMIN("admins", "admin"),
  MEMBER("members", "member"),
  MERCHANT("merchants", "merchant"),
  sa("superadmins", "sa");

  public final String label;

  public final String auth;

  RoleEnum(String label, String auth) {
    this.label = label;
    this.auth = auth;
  }
}
