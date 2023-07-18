/*    */
package com.solusinegeri.security.service.reactive.impl;
/*    */
/*    */

import com.solusinegeri.security.properties.SecurityProperties;
import com.solusinegeri.security.service.reactive.CookieService;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.time.Duration;

/*    */
/*    */
/*    */
/*    */ public class CookieServiceImpl
    /*    */ implements CookieService
    /*    */ {

  /*    */   private final SecurityProperties securityProperties;

  /*    */
  /*    */
  public CookieServiceImpl(SecurityProperties securityProperties) {
    /* 17 */
    this.securityProperties = securityProperties;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void removeSessionCookie(ServerHttpResponse response) {
    /* 22 */
    ResponseCookie expiredCookie = createExpiredCookie();
    /* 23 */
    response.addCookie(expiredCookie);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void setSessionCookie(ServerHttpResponse response, String sessionId) {
    /* 28 */
    ResponseCookie cookie = createCookie(sessionId);
    /* 29 */
    response.addCookie(cookie);
    /*    */
  }

  /*    */
  /*    */
  private ResponseCookie createCookie(String id) {
    /* 33 */
    return createCookie(id, this.securityProperties.getCookieMaxAge());
    /*    */
  }

  /*    */
  /*    */
  private ResponseCookie createCookie(String id, Duration maxAge) {
    /* 37 */
    String sessionId = (id != null) ? id : "";
    /* 38 */
    return ResponseCookie.from(this.securityProperties.getCookieSessionKey(), sessionId)
        /* 39 */.domain(this.securityProperties.getCookieDomain())
        /* 40 */.httpOnly(true)
        /* 41 */.maxAge(maxAge)
        /* 42 */.path(this.securityProperties.getCookiePath())
        /* 43 */.sameSite(this.securityProperties.getCookieSameSite())
        /* 44 */.secure(true)
        /* 45 */.build();
    /*    */
  }

  /*    */
  /*    */
  private ResponseCookie createExpiredCookie() {
    /* 49 */
    return createCookie(null, Duration.ZERO);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/reactive/impl/CookieServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */