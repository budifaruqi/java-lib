package com.solusinegeri.security.validator.servlet;

import com.solusinegeri.session.model.Session;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;

public interface SessionValidator extends Ordered {

  void validate(MethodParameter paramMethodParameter, Session paramSession);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/validator/servlet/SessionValidator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */