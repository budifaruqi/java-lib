package com.example.test.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

  public static final String AUTH_FAILED = "AUTH_FAILED";

  public static final String BOM_ALREADY_EXISTS = "BOM_ALREADY_EXISTS";

  public static final String BOM_NOT_EXIST = "BOM_NOT_EXIST";

  public static final String BOM_PRODUCTION_NOT_EXIST = "BOM_PRODUCTION_NOT_EXIST";

  public static final String CATEGORY_NOT_EXIST = "CATEGORY_NOT_EXIST";

  public static final String COMPANY_NOT_FOUND = "COMPANY_NOT_FOUND";

  public static final String INVALID_COMPANY_ID = "INVALID_COMPANY_ID";

  public static final String MAX_VALUE_IS_100 = "MAX_VALUE_IS_100";

  public static final String MIN_VALUE_IS_0 = "MIN_VALUE_IS_0";

  public static final String MIN_VALUE_IS_1 = "MIN_VALUE_IS_1";

  public static final String NAME_ALREADY_USED = "NAME_ALREADY_USED";

  public static final String PARTNER_CATEGORY_NOT_EXIST = "PARTNER_CATEGORY_NOT_EXIST";

  public static final String PARTNER_IS_NOT_A_CUSTOMER = "PARTNER_IS_NOT_A_CUSTOMER";

  public static final String PARTNER_IS_NOT_A_VENDOR = "PARTNER_IS_NOT_A_VENDOR";

  public static final String PARTNER_NOT_EXIST = "PARTNER_NOT_EXIST";

  public static final String BRAND_NOT_EXIST = "BRAND_NOT_EXIST";

  public static final String PARTNER_TAG_NOT_EXIST = "PARTNER_TAG_NOT_EXIST";

  public static final String PROCESSED_QTY_EXCEEDS_REQUESTED_QTY = "PROCESSED_QTY_EXCEEDS_REQUESTED_QTY";

  public static final String PRODUCT_CATEGORY_NOT_EXIST = "PRODUCT_CATEGORY_NOT_EXIST";

  public static final String PRODUCT_CODE_ALREADY_USED = "PRODUCT_CODE_ALREADY_USED";

  public static final String PRODUCT_NOT_AVAILABLE = "PRODUCT_NOT_AVAILABLE";

  public static final String PRODUCT_NOT_EXIST = "PRODUCT_NOT_EXIST";

  public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";

  public static final String PRODUCT_QUANTITY_NOT_EXIST = "PRODUCT_QUANTITY_NOT_EXIST";

  public static final String PRODUCT_STOCK_ALREADY_EXISTS = "PRODUCT_STOCK_ALREADY_EXISTS";

  public static final String PRODUCT_STOCK_NOT_ENOUGH = "PRODUCT_STOCK_NOT_ENOUGH";

  public static final String PRODUCT_STOCK_NOT_EXIST = "PRODUCT_STOCK_NOT_EXIST";

  public static final String PRODUCT_TAG_NOT_EXIST = "PRODUCT_TAG_NOT_EXIST";

  public static final String PURCHASE_REQUEST_NOT_EXIST = "PURCHASE_REQUEST_NOT_EXIST";

  public static final String PURCHASE_REQUEST_STATUS_NOT_VALID = "PURCHASE_REQUEST_STATUS_NOT_VALID";

  public static final String QUANTITY_MISMATCH = "QUANTITY_MISMATCH";

  public static final String STATUS_NOT_VALID = "STATUS_NOT_VALID";

  public static final String TRANSACTION_ID_NOT_IN_PURCHASE_REQUEST = "TRANSACTION_ID_NOT_IN_PURCHASE_REQUEST";

  public static final String TRANSACTION_NOT_EXIST = "TRANSACTION_NOT_EXIST";
}
