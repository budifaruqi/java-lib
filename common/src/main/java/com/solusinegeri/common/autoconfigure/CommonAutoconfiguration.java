/*    */
package com.solusinegeri.common.autoconfigure;
/*    */
/*    */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.common.service.ResourceService;
import com.solusinegeri.common.service.TimeService;
import com.solusinegeri.common.service.UuidService;
import com.solusinegeri.common.service.impl.JsonServiceImpl;
import com.solusinegeri.common.service.impl.ResourceServiceImpl;
import com.solusinegeri.common.service.impl.TimeServiceImpl;
import com.solusinegeri.common.service.impl.UuidServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*    */
/*    */
/*    */
@Configuration
/*    */
@PropertySource({"classpath:common.properties", "classpath:ValidationMessages.properties"})
/*    */ public class CommonAutoconfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public JsonService jsonService(ObjectMapper objectMapper) {
    /* 25 */
    return (JsonService) new JsonServiceImpl(objectMapper);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public ResourceService resourceService() {
    /* 31 */
    return (ResourceService) new ResourceServiceImpl();
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public TimeService timeService() {
    /* 37 */
    return (TimeService) new TimeServiceImpl();
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public UuidService uuidService() {
    /* 43 */
    return (UuidService) new UuidServiceImpl();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/autoconfigure/CommonAutoconfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */