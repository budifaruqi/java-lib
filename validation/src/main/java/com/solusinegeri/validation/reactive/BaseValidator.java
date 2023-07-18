/*    */
package com.solusinegeri.validation.reactive;
/*    */
/*    */

import org.apache.commons.lang3.BooleanUtils;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
    return ((Boolean) Mono.fromSupplier(() -> Boolean.valueOf(preValidation((T) value)))
        /* 17 */.filter(BooleanUtils::isTrue)
        /* 18 */.flatMap(test -> isValid((T) value))
        /* 19 */.blockOptional()
        /* 20 */.orElse(Boolean.TRUE)).booleanValue();
    /*    */
  }

  /*    */
  /*    */
  default boolean preValidation(T data) {
    /* 24 */
    return true;
    /*    */
  }

  /*    */
  /*    */   Mono<Boolean> isValid(T paramT);
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-validation/0.5.13/abinarystar-spring-validation-0.5.13.jar!/com/abinarystar/spring/validation/reactive/BaseValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */