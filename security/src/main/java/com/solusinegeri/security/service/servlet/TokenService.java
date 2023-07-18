package com.solusinegeri.security.service.servlet;

import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenService extends Ordered {

  Optional<String> get(HttpServletRequest paramHttpServletRequest);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/servlet/TokenService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */