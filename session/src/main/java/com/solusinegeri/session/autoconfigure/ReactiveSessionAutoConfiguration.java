/*    */
package com.solusinegeri.session.autoconfigure;
/*    */
/*    */

import com.solusinegeri.session.service.reactive.SessionService;
import com.solusinegeri.session.service.reactive.impl.InMemorySessionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
/*    */ public class ReactiveSessionAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public SessionService sessionService() {
    /* 18 */
    return (SessionService) new InMemorySessionService();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/autoconfigure/ReactiveSessionAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */