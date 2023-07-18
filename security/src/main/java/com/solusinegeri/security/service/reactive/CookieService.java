package com.solusinegeri.security.service.reactive;

import org.springframework.http.server.reactive.ServerHttpResponse;

public interface CookieService {

  void removeSessionCookie(ServerHttpResponse paramServerHttpResponse);

  void setSessionCookie(ServerHttpResponse paramServerHttpResponse, String paramString);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/reactive/CookieService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */