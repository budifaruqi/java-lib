/*    */
package com.solusinegeri.web.autoconfigure;
/*    */
/*    */

import com.solusinegeri.web.advice.ReactiveWebExceptionControllerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
/*    */
@Import({ReactiveWebExceptionControllerAdvice.class})
/*    */ public class ReactiveWebAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public Scheduler scheduler() {
    /* 21 */
    return Schedulers.boundedElastic();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/autoconfigure/ReactiveWebAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */