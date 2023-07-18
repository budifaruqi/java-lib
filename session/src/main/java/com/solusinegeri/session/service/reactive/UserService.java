package com.solusinegeri.session.service.reactive;

import com.solusinegeri.session.model.Session;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

  Mono<Boolean> hasPermission(Session paramSession, String paramString);

  Mono<Boolean> hasRole(Session paramSession, List<String> paramList);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/service/reactive/UserService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */