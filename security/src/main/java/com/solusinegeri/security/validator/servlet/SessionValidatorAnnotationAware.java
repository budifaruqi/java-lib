/*    */
package com.solusinegeri.security.validator.servlet;
/*    */
/*    */

import com.solusinegeri.security.helper.AnnotationHelper;
import com.solusinegeri.session.model.Session;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;

/*    */
/*    */
/*    */ public abstract class SessionValidatorAnnotationAware<A extends Annotation>
    /*    */ implements SessionValidator
    /*    */ {

  /*    */
  protected abstract Class<A> getValidatorAnnotationClass();

  /*    */
  /*    */
  protected abstract void throwIfInvalid(A paramA, Session paramSession);

  /*    */
  /*    */
  protected void throwIfInvalid(MethodParameter parameter, Session session) {
    /* 17 */
    AnnotationHelper.getAnnotation(parameter, getValidatorAnnotationClass())
        /* 18 */.ifPresent(annotation -> throwIfInvalid((A) annotation, session));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/servlet/SessionValidatorAnnotationAware.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */