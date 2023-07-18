/*    */
package com.solusinegeri.security.filter;
/*    */
/*    */

import com.solusinegeri.security.helper.SecurityHelper;
import com.solusinegeri.security.service.reactive.TokenService;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.reactive.SessionService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.List;
import java.util.Objects;

/*    */
/*    */ public class ReactiveSecurityFilter implements WebFilter {

  /*    */   private final SessionService sessionService;

  /*    */   private final List<TokenService> tokenServices;

  /*    */
  /*    */
  public ReactiveSecurityFilter(SessionService sessionService, List<TokenService> tokenServices) {
    /* 27 */
    this.sessionService = sessionService;
    /* 28 */
    this.tokenServices = tokenServices;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  @NonNull
  /*    */ public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
    /* 34 */
    return findSession(exchange.getRequest())
        /* 35 */.flatMap(session -> chain.filter(exchange)
            .contextWrite((ContextView) setSecurityContext(session))
            .then());
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  /*    */
  private Mono<Session> findSession(ServerHttpRequest request) {
    /* 42 */
    Objects.requireNonNull(this.sessionService);
    return getToken(request).flatMap(this.sessionService::find)
        /* 43 */.flatMap(session -> modifySession(request, session))
        /* 44 */.defaultIfEmpty(SecurityHelper.ANONYMOUS_SESSION);
    /*    */
  }

  /*    */
  /*    */
  private Mono<String> getToken(ServerHttpRequest request) {
    /* 48 */
    return Flux.fromIterable(this.tokenServices)
        /* 49 */.flatMap(tokenService -> tokenService.get(request))
        /* 50 */.next();
    /*    */
  }

  /*    */
  /*    */
  private Mono<Session> modifySession(ServerHttpRequest request, Session session) {
    /* 54 */
    return this.sessionService.modifySession(request, session)
        /* 55 */.thenReturn(session);
    /*    */
  }

  /*    */
  /*    */
  private Context setSecurityContext(Session session) {
    /* 59 */
    return ReactiveSecurityContextHolder.withAuthentication(
        (Authentication) SecurityHelper.createAuthentication(session));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/filter/ReactiveSecurityFilter.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */