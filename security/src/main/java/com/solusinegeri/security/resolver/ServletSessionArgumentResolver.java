/*    */
package com.solusinegeri.security.resolver;
/*    */
/*    */

import com.solusinegeri.security.validator.servlet.SessionValidator;
import com.solusinegeri.session.model.Session;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/*    */
/*    */
/*    */ public class ServletSessionArgumentResolver
    /*    */ implements HandlerMethodArgumentResolver
    /*    */ {

  /*    */   private final List<SessionValidator> sessionValidators;

  /*    */
  /*    */
  public ServletSessionArgumentResolver(List<SessionValidator> sessionValidators) {
    /* 22 */
    this.sessionValidators = sessionValidators;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public boolean supportsParameter(MethodParameter methodParameter) {
    /* 27 */
    return Session.class.isAssignableFrom(methodParameter.getParameterType());
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  /*    */
  public Object resolveArgument(@NonNull MethodParameter methodParameter,
      @Nullable ModelAndViewContainer modelAndViewContainer, @NonNull NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) {
    /* 34 */
    Session session = getSession();
    /* 35 */
    validateSession(methodParameter, session);
    /* 36 */
    return session;
    /*    */
  }

  /*    */
  /*    */
  private Session getSession() {
    /* 40 */
    return (Session) SecurityContextHolder.getContext()
        /* 41 */.getAuthentication()
        /* 42 */.getDetails();
    /*    */
  }

  /*    */
  /*    */
  private void validateSession(MethodParameter parameter, Session session) {
    /* 46 */
    this.sessionValidators.forEach(validator -> validator.validate(parameter, session));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/resolver/ServletSessionArgumentResolver.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */