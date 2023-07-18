package com.solusinegeri.security.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Authenticated {

  boolean value() default true;
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/model/annotation/Authenticated.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */