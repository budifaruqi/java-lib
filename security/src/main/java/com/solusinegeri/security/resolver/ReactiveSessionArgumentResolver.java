/*    */
package com.solusinegeri.security.resolver;
/*    */
/*    */

import com.solusinegeri.security.validator.reactive.SessionValidator;
import com.solusinegeri.session.model.Session;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/*    */
/*    */ public class ReactiveSessionArgumentResolver
    /*    */ implements HandlerMethodArgumentResolver
    /*    */ {

  /*    */   private final List<SessionValidator> sessionValidators;

  /*    */
  /*    */
  public ReactiveSessionArgumentResolver(List<SessionValidator> sessionValidators) {
    /* 24 */
    this.sessionValidators = sessionValidators;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public boolean supportsParameter(MethodParameter methodParameter) {
    /* 29 */
    return Session.class.isAssignableFrom(methodParameter.getParameterType());
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  @NonNull
  /*    */ public Mono<Object> resolveArgument(@NonNull MethodParameter methodParameter,
      @NonNull BindingContext bindingContext, @NonNull ServerWebExchange serverWebExchange) {
    /* 36 */
    return getSession()
        /* 37 */.filterWhen(session -> validateSession(methodParameter, session))
        /* 38 */.cast(Object.class);
    /*    */
  }

  /*    */
  /*    */
  private Mono<Session> getSession() {
    /* 42 */
    return ReactiveSecurityContextHolder.getContext()
        /* 43 */.map(SecurityContext::getAuthentication)
        /* 44 */.map(Authentication::getDetails)
        /* 45 */.cast(Session.class);
    /*    */
  }

  /*    */
  /*    */
  private Mono<Boolean> validateSession(MethodParameter parameter, Session session) {
    /* 49 */
    return Flux.fromIterable(this.sessionValidators)
        /* 50 */.flatMap(validator -> validator.validate(parameter, session))
        /* 51 */.then()
        /* 52 */.thenReturn(Boolean.TRUE);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/resolver/ReactiveSessionArgumentResolver.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */