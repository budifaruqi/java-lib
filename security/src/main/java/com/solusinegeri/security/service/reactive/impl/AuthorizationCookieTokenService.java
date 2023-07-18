/*    */
package com.solusinegeri.security.service.reactive.impl;
/*    */
/*    */

import com.solusinegeri.security.properties.SecurityProperties;
import com.solusinegeri.security.service.reactive.TokenService;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;

/*    */
/*    */ public class AuthorizationCookieTokenService implements TokenService {

  /*    */   private final SecurityProperties securityProperties;

  /*    */
  /*    */
  public AuthorizationCookieTokenService(SecurityProperties securityProperties) {
    /* 15 */
    this.securityProperties = securityProperties;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<String> get(ServerHttpRequest request) {
    /* 20 */
    Objects.requireNonNull(request);
    return Mono.fromSupplier(request::getCookies)
        /* 21 */.flatMap(cookies -> Mono.justOrEmpty(cookies.getFirst(this.securityProperties.getCookieSessionKey())))
        /* 22 */.map(HttpCookie::getValue);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 27 */
    return 2;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/reactive/impl/AuthorizationCookieTokenService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */