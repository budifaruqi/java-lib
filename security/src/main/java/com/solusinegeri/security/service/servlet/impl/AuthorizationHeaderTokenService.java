/*    */
package com.solusinegeri.security.service.servlet.impl;
/*    */
/*    */

import com.solusinegeri.security.service.servlet.TokenService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/*    */
/*    */
/*    */ public class AuthorizationHeaderTokenService
    /*    */ implements TokenService
    /*    */ {

  /*    */
  public Optional<String> get(HttpServletRequest request) {
    /* 12 */
    return Optional.<String>ofNullable(request.getHeader("Authorization"))
        /* 13 */.filter(auth -> (auth.length() > 7))
        /* 14 */.map(auth -> auth.substring(7));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 19 */
    return 1;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/servlet/impl/AuthorizationHeaderTokenService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */