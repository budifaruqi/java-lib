package com.example.test.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

  public static final String BOM_ALREADY_EXISTS = "BOM_ALREADY_EXISTS";

  public static final String BOM_NOT_EXIST = "BOM_NOT_EXIST";

  public static final String CATEGORY_NOT_EXIST = "CATEGORY_NOT_EXIST";

  public static final String MAX_VALUE_IS_100 = "MAX_VALUE_IS_100";

  public static final String MIN_VALUE_IS_0 = "MIN_VALUE_IS_0";

  public static final String NAME_ALREADY_USED = "NAME_ALREADY_USED";

  public static final String PARTNER_CATEGORY_NOT_EXIST = "PARTNER_CATEGORY_NOT_EXIST";

  public static final String PARTNER_NOT_EXIST = "PARTNER_NOT_EXIST";

  public static final String BRAND_NOT_EXIST = "BRAND_NOT_EXIST";

  public static final String PRODUCT_CATEGORY_NOT_EXIST = "PRODUCT_CATEGORY_NOT_EXIST";

  public static final String PRODUCT_CODE_ALREADY_USED = "PRODUCT_CODE_ALREADY_USED";

  public static final String PRODUCT_NOT_EXIST = "PRODUCT_NOT_EXIST";

  public static final String PRODUCT_QUANTITY_NOT_EXIST = "PRODUCT_QUANTITY_NOT_EXIST";

  public static final String PRODUCT_STOCK_ALREADY_EXISTS = "PRODUCT_STOCK_ALREADY_EXISTS";

  public static final String PRODUCT_STOCK_NOT_ENOUGH = "PRODUCT_STOCK_NOT_ENOUGH";

  public static final String PRODUCT_STOCK_NOT_EXIST = "PRODUCT_STOCK_NOT_EXIST";
}
