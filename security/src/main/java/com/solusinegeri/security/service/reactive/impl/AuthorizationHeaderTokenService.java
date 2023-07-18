/*    */
package com.solusinegeri.security.service.reactive.impl;
/*    */
/*    */

import com.solusinegeri.security.service.reactive.TokenService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

/*    */
/*    */ public class AuthorizationHeaderTokenService
    /*    */ implements TokenService
    /*    */ {

  /*    */
  public Mono<String> get(ServerHttpRequest request) {
    /* 14 */
    Objects.requireNonNull(request);
    return Mono.fromSupplier(request::getHeaders)
        /* 15 */.map(headers -> Optional.ofNullable(headers.getFirst("Authorization")))
        /* 16 */.filter(Optional::isPresent)
        /* 17 */.map(Optional::get)
        /* 18 */.filter(auth -> (auth.length() > 7))
        /* 19 */.map(auth -> auth.substring(7));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 24 */
    return 1;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/reactive/impl/AuthorizationHeaderTokenService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */