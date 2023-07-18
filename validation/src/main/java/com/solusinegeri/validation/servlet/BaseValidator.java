/*    */
package com.solusinegeri.validation.servlet;
/*    */
/*    */

import org.apache.commons.lang3.BooleanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public interface BaseValidator<A extends java.lang.annotation.Annotation, T>
    /*    */ extends ConstraintValidator<A, T>
    /*    */ {

  /*    */
  default boolean isValid(T value, ConstraintValidatorContext context) {
    /* 16 */
    return ((Boolean) Optional.<Boolean>of(Boolean.valueOf(preValidation(value)))
        /* 17 */.filter(BooleanUtils::isTrue)
        /* 18 */.map(test -> Boolean.valueOf(isValid((T) value)))
        /* 19 */.orElse(Boolean.TRUE)).booleanValue();
    /*    */
  }

  /*    */
  /*    */
  default boolean preValidation(T data) {
    /* 23 */
    return true;
    /*    */
  }

  /*    */
  /*    */   boolean isValid(T paramT);
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-validation/0.5.13/abinarystar-spring-validation-0.5.13.jar!/com/abinarystar/spring/validation/servlet/BaseValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */