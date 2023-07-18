/*    */
package com.solusinegeri.security.validator.reactive;
/*    */
/*    */

import com.solusinegeri.security.helper.AnnotationHelper;
import com.solusinegeri.session.model.Session;
import org.springframework.core.MethodParameter;
import reactor.core.publisher.Mono;

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
  protected abstract Mono<Void> throwIfInvalid(A paramA, Session paramSession);

  /*    */
  /*    */
  protected Mono<Void> throwIfInvalid(MethodParameter parameter, Session session) {
    /* 18 */
    return Mono.justOrEmpty(AnnotationHelper.getAnnotation(parameter, getValidatorAnnotationClass()))
        /* 19 */.flatMap(annotation -> throwIfInvalid((A) annotation, session));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/reactive/SessionValidatorAnnotationAware.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */