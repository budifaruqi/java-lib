/*    */
package com.solusinegeri.security.validator.servlet.impl;
/*    */
/*    */

import com.solusinegeri.common.model.exception.ForbiddenException;
import com.solusinegeri.security.model.annotation.HasRole;
import com.solusinegeri.security.validator.servlet.SessionValidatorAnnotationAware;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.servlet.UserService;
import org.springframework.core.MethodParameter;

import java.util.List;

/*    */
/*    */ public class RoleSessionValidator
    /*    */ extends SessionValidatorAnnotationAware<HasRole>
    /*    */ {

  /*    */   private final UserService userService;

  /*    */
  /*    */
  public RoleSessionValidator(UserService userService) {
    /* 18 */
    this.userService = userService;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 23 */
    return 3;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void validate(MethodParameter parameter, Session session) {
    /* 28 */
    throwIfInvalid(parameter, session);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Class<HasRole> getValidatorAnnotationClass() {
    /* 33 */
    return HasRole.class;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected void throwIfInvalid(HasRole annotation, Session session) {
    /* 38 */
    if (!isRolePresent(annotation, session)) {
      /* 39 */
      throw new ForbiddenException();
      /*    */
    }
    /*    */
  }

  /*    */
  /*    */
  private boolean isRolePresent(HasRole annotation, Session session) {
    /* 44 */
    return this.userService.hasRole(session, List.of(annotation.value()));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/servlet/impl/RoleSessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */