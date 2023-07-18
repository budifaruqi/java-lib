/*    */
package com.solusinegeri.security.service.servlet.impl;
/*    */
/*    */

import com.solusinegeri.security.properties.SecurityProperties;
import com.solusinegeri.security.service.servlet.CookieService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/*    */
/*    */
/*    */ public class CookieServiceImpl
    /*    */ implements CookieService
    /*    */ {

  /*    */   private final SecurityProperties securityProperties;

  /*    */
  /*    */
  public CookieServiceImpl(SecurityProperties securityProperties) {
    /* 16 */
    this.securityProperties = securityProperties;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void removeSessionCookie(HttpServletResponse response) {
    /* 21 */
    Cookie expiredCookie = createExpiredCookie();
    /* 22 */
    response.addCookie(expiredCookie);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void setSessionCookie(HttpServletResponse response, String sessionId) {
    /* 27 */
    Cookie cookie = createCookie(sessionId);
    /* 28 */
    response.addCookie(cookie);
    /*    */
  }

  /*    */
  /*    */
  private Cookie createCookie(String id) {
    /* 32 */
    return createCookie(id, getCookieMaxAge());
    /*    */
  }

  /*    */
  /*    */
  private Cookie createCookie(String id, int maxAge) {
    /* 36 */
    String cookieId = (id != null) ? id : "";
    /* 37 */
    Cookie cookie = new Cookie(this.securityProperties.getCookieSessionKey(), cookieId);
    /* 38 */
    String cookieDomain = this.securityProperties.getCookieDomain();
    /* 39 */
    if (StringUtils.isNotBlank(cookieDomain)) {
      /* 40 */
      cookie.setDomain(cookieDomain);
      /*    */
    }
    /* 42 */
    cookie.setHttpOnly(true);
    /* 43 */
    cookie.setMaxAge(maxAge);
    /* 44 */
    cookie.setPath(this.securityProperties.getCookiePath());
    /* 45 */
    cookie.setSecure(true);
    /* 46 */
    return cookie;
    /*    */
  }

  /*    */
  /*    */
  private Cookie createExpiredCookie() {
    /* 50 */
    return createCookie(null, 0);
    /*    */
  }

  /*    */
  /*    */
  private int getCookieMaxAge() {
    /* 54 */
    return
        /* 55 */       (int) this.securityProperties.getCookieMaxAge()
        .toSeconds();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/servlet/impl/CookieServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */