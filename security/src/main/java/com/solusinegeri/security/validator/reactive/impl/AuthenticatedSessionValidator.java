/*    */
package com.solusinegeri.security.validator.reactive.impl;
/*    */
/*    */

import com.solusinegeri.security.model.annotation.Authenticated;
import com.solusinegeri.security.validator.reactive.SessionValidatorAnnotationAware;
import com.solusinegeri.session.model.Session;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import reactor.core.publisher.Mono;

/*    */
/*    */ public class AuthenticatedSessionValidator
    /*    */ extends SessionValidatorAnnotationAware<Authenticated>
    /*    */ {

  /*    */
  public int getOrder() {
    /* 17 */
    return 1;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<Void> validate(MethodParameter parameter, Session session) {
    /* 22 */
    return throwIfInvalid(parameter, session);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Class<Authenticated> getValidatorAnnotationClass() {
    /* 27 */
    return Authenticated.class;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Mono<Void> throwIfInvalid(Authenticated annotation, Session session) {
    /* 32 */
    return Mono.fromSupplier(() -> Boolean.valueOf(isAuthenticated(annotation, session)))
        /* 33 */.filter(BooleanUtils::isTrue)
        /* 34 */.switchIfEmpty(Mono.error(com.solusinegeri.common.model.exception.UnauthorizedException::new))
        /* 35 */.then();
    /*    */
  }

  /*    */
  /*    */
  private boolean isAuthenticated(Authenticated annotation, Session session) {
    /* 39 */
    return (!needValidateAuthentication(annotation) || StringUtils.isNotBlank(session.getUserId()));
    /*    */
  }

  /*    */
  /*    */
  private boolean needValidateAuthentication(Authenticated annotation) {
    /* 43 */
    return annotation.value();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/reactive/impl/AuthenticatedSessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */