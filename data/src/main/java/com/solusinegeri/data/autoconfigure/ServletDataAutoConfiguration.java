/*    */
package com.solusinegeri.data.autoconfigure;
/*    */
/*    */

import com.solusinegeri.data.auditoraware.ServletAuditorAwareImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/*    */ public class ServletDataAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnClass(name = {"org.springframework.security.core.context.SecurityContextHolder"})
  /*    */
  @ConditionalOnMissingBean
  /*    */ public AuditorAware<String> auditorAware() {
    /* 20 */
    return (AuditorAware<String>) new ServletAuditorAwareImpl();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/autoconfigure/ServletDataAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */