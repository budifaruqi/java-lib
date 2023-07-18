/*    */
package com.solusinegeri.security.validator.reactive.impl;
/*    */
/*    */

import com.solusinegeri.security.model.annotation.HasRole;
import com.solusinegeri.security.validator.reactive.SessionValidatorAnnotationAware;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.reactive.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.MethodParameter;
import reactor.core.publisher.Mono;

import java.util.List;

/*    */
/*    */ public class RoleSessionValidator
    /*    */ extends SessionValidatorAnnotationAware<HasRole>
    /*    */ {

  /*    */   private final UserService userService;

  /*    */
  /*    */
  public RoleSessionValidator(UserService userService) {
    /* 20 */
    this.userService = userService;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 25 */
    return 3;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<Void> validate(MethodParameter parameter, Session session) {
    /* 30 */
    return throwIfInvalid(parameter, session);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Class<HasRole> getValidatorAnnotationClass() {
    /* 35 */
    return HasRole.class;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Mono<Void> throwIfInvalid(HasRole annotation, Session session) {
    /* 40 */
    return isRolePresent(annotation, session)
        /* 41 */.filter(BooleanUtils::isTrue)
        /* 42 */.switchIfEmpty(Mono.error(com.solusinegeri.common.model.exception.ForbiddenException::new))
        /* 43 */.then();
    /*    */
  }

  /*    */
  /*    */
  private Mono<Boolean> isRolePresent(HasRole annotation, Session session) {
    /* 47 */
    return this.userService.hasRole(session, List.of(annotation.value()));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/reactive/impl/RoleSessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */