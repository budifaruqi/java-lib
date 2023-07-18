/*    */
package com.solusinegeri.session.autoconfigure;
/*    */
/*    */

import com.solusinegeri.session.service.servlet.SessionService;
import com.solusinegeri.session.service.servlet.impl.InMemorySessionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/*    */ public class ServletSessionAutoConfiguration
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


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-session/0.5.13/abinarystar-spring-session-0.5.13.jar!/com/abinarystar/spring/session/autoconfigure/ServletSessionAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */