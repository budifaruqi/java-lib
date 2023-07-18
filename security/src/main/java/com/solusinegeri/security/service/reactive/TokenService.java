package com.solusinegeri.security.service.reactive;

import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface TokenService extends Ordered {

  Mono<String> get(ServerHttpRequest paramServerHttpRequest);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/service/reactive/TokenService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */