package com.solusinegeri.session.service.servlet;

import com.solusinegeri.session.model.Session;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface SessionService {

  void delete(String paramString);

  Optional<Session> find(String paramString);

  void modifySession(HttpServletRequest paramHttpServletRequest, Session paramSession);

  Session save(Session paramSession);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/service/servlet/SessionService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */