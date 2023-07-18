package com.solusinegeri.web.autoconfigure;

import com.solusinegeri.web.advice.ServletWebExceptionControllerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import({ServletWebExceptionControllerAdvice.class})
public class ServletWebAutoConfiguration {}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/autoconfigure/ServletWebAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */