/*    */
package com.solusinegeri.kafka.producer.servlet.impl;
/*    */
/*    */

import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.kafka.producer.servlet.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;

/*    */
/*    */
/*    */ public class KafkaProducerImpl
    /*    */ implements KafkaProducer
    /*    */ {

  /*    */   private final JsonService jsonService;

  /*    */   private final KafkaTemplate<String, String> kafkaTemplate;

  /*    */
  /*    */
  public KafkaProducerImpl(JsonService jsonService, KafkaTemplate<String, String> kafkaTemplate) {
    /* 15 */
    this.jsonService = jsonService;
    /* 16 */
    this.kafkaTemplate = kafkaTemplate;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void send(String topic, Object data) {
    /* 21 */
    this.kafkaTemplate.send(topic, this.jsonService.map(data));
    /*    */
  }

  /*    */
  /*    */
  public void send(String topic, String key, Object data) {
    /* 25 */
    this.kafkaTemplate.send(topic, key, this.jsonService.map(data));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-kafka/0.5.13/abinarystar-spring-kafka-0.5.13.jar!/com/abinarystar/spring/kafka/producer/servlet/impl/KafkaProducerImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */