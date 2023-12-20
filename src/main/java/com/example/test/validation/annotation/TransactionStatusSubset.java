package com.example.test.validation.annotation;

import com.example.test.common.constant.ErrorCode;
import com.example.test.common.enums.TransactionStatus;
import com.example.test.validation.validator.TransactionStatusSubsetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = TransactionStatusSubsetValidator.class)

public @interface TransactionStatusSubset {

  TransactionStatus[] anyOf();

  Class<?>[] groups() default {};

  String message() default ErrorCode.STATUS_NOT_VALID;

  Class<? extends Payload>[] payload() default {};
}
