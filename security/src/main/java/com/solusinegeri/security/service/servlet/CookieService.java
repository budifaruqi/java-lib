package com.solusinegeri.security.service.servlet;

import javax.servlet.http.HttpServletResponse;

public interface CookieService {

  void removeSessionCookie(HttpServletResponse paramHttpServletResponse);

  void setSessionCookie(HttpServletResponse paramHttpServletResponse, String paramString);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/servlet/CookieService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */