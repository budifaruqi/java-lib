/*    */
package com.solusinegeri.security.configuration;
/*    */
/*    */

import com.solusinegeri.security.resolver.ServletSessionArgumentResolver;
import com.solusinegeri.security.validator.servlet.SessionValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/*    */
/*    */
/*    */
@Configuration
/*    */ public class ServletSecurityWebConfiguration
    /*    */ implements WebMvcConfigurer
    /*    */ {

  /*    */   private final List<SessionValidator> sessionValidators;

  /*    */
  /*    */
  public ServletSecurityWebConfiguration(List<SessionValidator> sessionValidators) {
    /* 18 */
    this.sessionValidators = sessionValidators;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    /* 23 */
    resolvers.add(new ServletSessionArgumentResolver(this.sessionValidators));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/configuration/ServletSecurityWebConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */