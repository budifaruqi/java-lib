/*    */
package com.solusinegeri.data.autoconfigure;
/*    */
/*    */

import com.solusinegeri.data.auditoraware.ReactiveAuditorAwareImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;

/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
/*    */ public class ReactiveDataAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnClass(name = {"org.springframework.security.core.context.ReactiveSecurityContextHolder"})
  /*    */
  @ConditionalOnMissingBean
  /*    */ public ReactiveAuditorAware<String> auditorAware() {
    /* 20 */
    return (ReactiveAuditorAware<String>) new ReactiveAuditorAwareImpl();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/autoconfigure/ReactiveDataAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */