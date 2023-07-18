package com.solusinegeri.session.service.reactive;

import com.solusinegeri.session.model.Session;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface SessionService {

  Mono<Void> delete(String paramString);

  Mono<Session> find(String paramString);

  Mono<Void> modifySession(ServerHttpRequest paramServerHttpRequest, Session paramSession);

  Mono<Session> save(Session paramSession);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/service/reactive/SessionService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */