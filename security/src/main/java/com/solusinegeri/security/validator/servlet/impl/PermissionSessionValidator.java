/*    */
package com.solusinegeri.security.validator.servlet.impl;
/*    */
/*    */

import com.solusinegeri.common.model.exception.ForbiddenException;
import com.solusinegeri.security.model.annotation.HasPermission;
import com.solusinegeri.security.validator.servlet.SessionValidatorAnnotationAware;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.servlet.UserService;
import org.springframework.core.MethodParameter;

/*    */
/*    */ public class PermissionSessionValidator
    /*    */ extends SessionValidatorAnnotationAware<HasPermission> {

  /*    */   private final UserService userService;

  /*    */
  /*    */
  public PermissionSessionValidator(UserService userService) {
    /* 16 */
    this.userService = userService;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 21 */
    return 2;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void validate(MethodParameter parameter, Session session) {
    /* 26 */
    throwIfInvalid(parameter, session);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Class<HasPermission> getValidatorAnnotationClass() {
    /* 31 */
    return HasPermission.class;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected void throwIfInvalid(HasPermission annotation, Session session) {
    /* 36 */
    if (!isUserPermitted(annotation, session)) {
      /* 37 */
      throw new ForbiddenException();
      /*    */
    }
    /*    */
  }

  /*    */
  /*    */
  private boolean isUserPermitted(HasPermission annotation, Session session) {
    /* 42 */
    return this.userService.hasPermission(session, annotation.value());
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/servlet/impl/PermissionSessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */