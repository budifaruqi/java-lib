/*    */
package com.solusinegeri.session.service.reactive.impl;
/*    */
/*    */

import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.reactive.SessionService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/*    */
/*    */
/*    */ public class InMemorySessionService
    /*    */ implements SessionService
    /*    */ {

  /* 14 */   private final Map<String, Session> sessionById = new HashMap<>();

  /*    */
  /*    */
  /*    */
  public Mono<Void> delete(String id) {
    /* 18 */
    return Mono.fromRunnable(() -> this.sessionById.remove(id));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<Session> find(String id) {
    /* 23 */
    return Mono.fromSupplier(() -> (Session) this.sessionById.get(id));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<Void> modifySession(ServerHttpRequest request, Session session) {
    /* 28 */
    return Mono.empty();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Mono<Session> save(Session session) {
    /* 33 */
    return Mono.fromRunnable(() -> this.sessionById.put(session.getId(), session))
        /* 34 */.thenReturn(session);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/service/reactive/impl/InMemorySessionService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */