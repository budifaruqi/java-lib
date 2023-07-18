/*    */
package com.solusinegeri.security.helper;
/*    */
/*    */

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Optional;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class AnnotationHelper
    /*    */ {

  /*    */
  public static <A extends Annotation> Optional<A> getAnnotation(MethodParameter methodParameter,
      Class<A> annotationClass) {
    /* 18 */
    Annotation annotation = Optional.<Annotation>ofNullable(getAnnotationFromMethod(methodParameter, annotationClass))
        .orElseGet(() -> getAnnotationFromType(methodParameter, annotationClass));
    /* 19 */
    return Optional.ofNullable((A) annotation);
    /*    */
  }

  /*    */
  /*    */
  private static <A extends Annotation> A getAnnotation(AnnotatedElement element, Class<A> annotationClass) {
    /* 23 */
    return (A) AnnotatedElementUtils.getMergedAnnotation(element, annotationClass);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  private static <A extends Annotation> A getAnnotationFromMethod(MethodParameter methodParameter,
      Class<A> annotationClass) {
    /* 28 */
    return (A) Optional.<Method>ofNullable(methodParameter.getMethod())
        /* 29 */.map(method -> getAnnotation(method, annotationClass))
        /* 30 */.orElse(null);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  private static <A extends Annotation> A getAnnotationFromType(MethodParameter methodParameter,
      Class<A> annotationClass) {
    /* 35 */
    return (A) Optional.<Method>ofNullable(methodParameter.getMethod())
        /* 36 */.map(method -> getAnnotation(method.getDeclaringClass(), annotationClass))
        /* 37 */.orElse(null);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/helper/AnnotationHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */