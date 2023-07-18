/*    */
package com.solusinegeri.security.validator.reactive.impl;
/*    */
/*    */

import com.solusinegeri.security.model.annotation.HasPermission;
import com.solusinegeri.security.validator.reactive.SessionValidatorAnnotationAware;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.reactive.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.MethodParameter;
import reactor.core.publisher.Mono;

/*    */
/*    */ public class PermissionSessionValidator
    /*    */ extends SessionValidatorAnnotationAware<HasPermission> {

  /*    */   private final UserService userService;

  /*    */
  /*    */
  public PermissionSessionValidator(UserService userService) {
    /* 18 */
    this.userService = userService;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 23 */
    return 2;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<Void> validate(MethodParameter parameter, Session session) {
    /* 28 */
    return throwIfInvalid(parameter, session);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Class<HasPermission> getValidatorAnnotationClass() {
    /* 33 */
    return HasPermission.class;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Mono<Void> throwIfInvalid(HasPermission annotation, Session session) {
    /* 38 */
    return isUserPermitted(annotation, session)
        /* 39 */.filter(BooleanUtils::isTrue)
        /* 40 */.switchIfEmpty(Mono.error(com.solusinegeri.common.model.exception.ForbiddenException::new))
        /* 41 */.then();
    /*    */
  }

  /*    */
  /*    */
  private Mono<Boolean> isUserPermitted(HasPermission annotation, Session session) {
    /* 45 */
    return this.userService.hasPermission(session, annotation.value());
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/reactive/impl/PermissionSessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */