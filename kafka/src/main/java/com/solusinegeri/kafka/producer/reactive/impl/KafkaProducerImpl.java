/*    */
package com.solusinegeri.kafka.producer.reactive.impl;
/*    */

import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.kafka.producer.reactive.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/*    */
/*    */ public class KafkaProducerImpl implements KafkaProducer {

  /* 11 */   private static final Logger log = LoggerFactory.getLogger(KafkaProducerImpl.class);

  /*    */
  /*    */   private final JsonService jsonService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final Scheduler scheduler;

  public KafkaProducerImpl(JsonService jsonService, KafkaTemplate<String, String> kafkaTemplate, Scheduler scheduler) {
    /* 21 */
    this.jsonService = jsonService;
    /* 22 */
    this.kafkaTemplate = kafkaTemplate;
    /* 23 */
    this.scheduler = scheduler;
    /*    */
  }

  public void send(String topic, Object data) {
    /* 28 */
    this.kafkaTemplate.send(topic, this.jsonService.map(data));
    /*    */
  }

  /*    */
  /*    */
  public void send(String topic, String key, Object data) {
    /* 32 */
    this.kafkaTemplate.send(topic, key, this.jsonService.map(data));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void sendAsync(String topic, Object data) {
    Mono.fromSupplier(() -> this.kafkaTemplate.send(topic, this.jsonService.map(data)))
        /* 38 */.doOnError(throwable -> log.warn("Kafka exception: {}", throwable.getMessage()))
        /* 39 */.onErrorResume(throwable -> Mono.empty())
        /* 40 */.subscribeOn(this.scheduler)
        /* 41 */.subscribe();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void sendAsync(String topic, String key, Object data) {
    Mono.fromSupplier(() -> this.kafkaTemplate.send(topic, key, this.jsonService.map(data)))
        /* 47 */.doOnError(throwable -> log.warn("Kafka exception: {}", throwable.getMessage()))
        /* 48 */.onErrorResume(throwable -> Mono.empty())
        /* 49 */.subscribeOn(this.scheduler)
        /* 50 */.subscribe();
  }
}