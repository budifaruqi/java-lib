package com.example.test.validation.annotation;

import com.example.test.common.constant.ErrorCode;
import com.example.test.validation.validator.InternalPartnerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
@Constraint(validatedBy = InternalPartnerValidator.class)
public @interface InternalPartner {

  Class<?>[] groups() default {};

  String message() default ErrorCode.INVALID_COMPANY_ID;

  Class<? extends Payload>[] payload() default {};
}
