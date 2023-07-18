package com.solusinegeri.session.service.servlet;

import com.solusinegeri.session.model.Session;

import java.util.List;

public interface UserService {

  boolean hasPermission(Session paramSession, String paramString);

  boolean hasRole(Session paramSession, List<String> paramList);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/service/servlet/UserService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */