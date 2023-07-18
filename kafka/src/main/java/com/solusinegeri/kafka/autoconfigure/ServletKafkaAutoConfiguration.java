/*    */
package com.solusinegeri.kafka.autoconfigure;
/*    */
/*    */

import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.kafka.producer.servlet.KafkaProducer;
import com.solusinegeri.kafka.producer.servlet.impl.KafkaProducerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/*    */ public class ServletKafkaAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public KafkaProducer kafkaProducer(JsonService jsonService, KafkaTemplate<String, String> kafkaTemplate) {
    /* 20 */
    return (KafkaProducer) new KafkaProducerImpl(jsonService, kafkaTemplate);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-kafka/0.5.13/abinarystar-spring-kafka-0.5.13.jar!/com/abinarystar/spring/kafka/autoconfigure/ServletKafkaAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */