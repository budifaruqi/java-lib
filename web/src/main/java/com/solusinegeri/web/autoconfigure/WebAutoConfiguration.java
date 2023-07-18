/*    */
package com.solusinegeri.web.autoconfigure;
/*    */
/*    */

import com.solusinegeri.web.converter.DateConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*    */
/*    */
/*    */
@Configuration
/*    */ public class WebAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public DateConverter dateConverter() {
    /* 15 */
    return new DateConverter();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/autoconfigure/WebAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */