package com.solusinegeri.kafka.autoconfigure;

import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.kafka.producer.reactive.KafkaProducer;
import com.solusinegeri.kafka.producer.reactive.impl.KafkaProducerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.scheduler.Scheduler;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveKafkaAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public KafkaProducer kafkaProducer(JsonService jsonService, KafkaTemplate<String, String> kafkaTemplate,
      Scheduler scheduler) {

    return new KafkaProducerImpl(jsonService, kafkaTemplate, scheduler);
  }
}
