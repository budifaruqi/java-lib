/*    */
package com.solusinegeri.security.service.servlet.impl;
/*    */
/*    */

import com.solusinegeri.security.properties.SecurityProperties;
import com.solusinegeri.security.service.servlet.TokenService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/*    */
/*    */ public class AuthorizationCookieTokenService
    /*    */ implements TokenService
    /*    */ {

  /*    */   private final SecurityProperties securityProperties;

  /*    */
  /*    */
  public AuthorizationCookieTokenService(SecurityProperties securityProperties) {
    /* 19 */
    this.securityProperties = securityProperties;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Optional<String> get(HttpServletRequest request) {
    /* 24 */
    return (Optional.<Cookie[]>ofNullable(request.getCookies())
        /* 25 */.map(Arrays::asList)
        /* 26 */.orElseGet(Collections::emptyList))
        /* 27 */.stream()
        /* 28 */.filter(cookie -> StringUtils.equals(this.securityProperties.getCookieSessionKey(), cookie.getName()))
        /* 29 */.map(Cookie::getValue)
        /* 30 */.findFirst();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getOrder() {
    /* 35 */
    return 2;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/servlet/impl/AuthorizationCookieTokenService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */