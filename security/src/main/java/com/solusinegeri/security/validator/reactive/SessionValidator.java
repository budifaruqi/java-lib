package com.solusinegeri.security.validator.reactive;

import com.solusinegeri.session.model.Session;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import reactor.core.publisher.Mono;

public interface SessionValidator extends Ordered {

  Mono<Void> validate(MethodParameter paramMethodParameter, Session paramSession);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/reactive/SessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */