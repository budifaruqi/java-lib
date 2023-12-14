/*    */
package com.solusinegeri.session.service.servlet.impl;
/*    */
/*    */

import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.servlet.SessionService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*    */
/*    */ public class InMemorySessionService
    /*    */ implements SessionService
    /*    */ {

  /* 13 */   private final Map<String, Session> sessionById = new HashMap<>();

  /*    */
  /*    */
  /*    */
  public void delete(String id) {
    /* 17 */
    this.sessionById.remove(id);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Optional<Session> find(String id) {
    /* 22 */
    return Optional.ofNullable(this.sessionById.get(id));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  public void modifySession(HttpServletRequest request, Session session) {
  }

  /*    */
  /*    */
  /*    */
  public Session save(Session session) {
    /* 31 */
    this.sessionById.put(session.getId(), session);
    /* 32 */
    return session;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/service/servlet/impl/InMemorySessionService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */