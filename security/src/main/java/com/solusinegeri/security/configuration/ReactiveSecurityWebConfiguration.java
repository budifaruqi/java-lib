/*    */
package com.solusinegeri.security.configuration;
/*    */
/*    */

import com.solusinegeri.security.resolver.ReactiveSessionArgumentResolver;
import com.solusinegeri.security.validator.reactive.SessionValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

import java.util.List;

/*    */
/*    */
@Configuration
/*    */ public class ReactiveSecurityWebConfiguration
    /*    */ implements WebFluxConfigurer
    /*    */ {

  /*    */   private final List<SessionValidator> sessionValidators;

  /*    */
  /*    */
  public ReactiveSecurityWebConfiguration(List<SessionValidator> sessionValidators) {
    /* 18 */
    this.sessionValidators = sessionValidators;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    /* 23 */
    configurer.addCustomResolver(new HandlerMethodArgumentResolver[]{
        (HandlerMethodArgumentResolver) new ReactiveSessionArgumentResolver(this.sessionValidators)});
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/configuration/ReactiveSecurityWebConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */