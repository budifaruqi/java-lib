/*    */
package com.solusinegeri.security.validator.servlet.impl;
/*    */
/*    */

import com.solusinegeri.common.model.exception.UnauthorizedException;
import com.solusinegeri.security.model.annotation.Authenticated;
import com.solusinegeri.security.validator.servlet.SessionValidatorAnnotationAware;
import com.solusinegeri.session.model.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;

/*    */
/*    */ public class AuthenticatedSessionValidator
    /*    */ extends SessionValidatorAnnotationAware<Authenticated>
    /*    */ {

  /*    */
  public int getOrder() {
    /* 15 */
    return 1;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void validate(MethodParameter parameter, Session session) {
    /* 20 */
    throwIfInvalid(parameter, session);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Class<Authenticated> getValidatorAnnotationClass() {
    /* 25 */
    return Authenticated.class;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected void throwIfInvalid(Authenticated annotation, Session session) {
    /* 30 */
    if (!isAuthenticated(annotation, session)) {
      /* 31 */
      throw new UnauthorizedException();
      /*    */
    }
    /*    */
  }

  /*    */
  /*    */
  private boolean isAuthenticated(Authenticated annotation, Session session) {
    /* 36 */
    return (!needValidateAuthentication(annotation) || StringUtils.isNotBlank(session.getUserId()));
    /*    */
  }

  /*    */
  /*    */
  private boolean needValidateAuthentication(Authenticated annotation) {
    /* 40 */
    return annotation.value();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/servlet/impl/AuthenticatedSessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */