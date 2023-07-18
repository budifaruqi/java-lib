package com.solusinegeri.kafka.listener.reactive;

import com.solusinegeri.common.model.exception.BaseException;
import com.solusinegeri.common.service.JsonService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public abstract class BaseKafkaListener<T> {

  protected final JsonService jsonService;

  private final Class<T> eventClass;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public BaseKafkaListener(JsonService jsonService, Class<T> eventClass) {
    this.jsonService = jsonService;
    this.eventClass = eventClass;
  }

  protected void execute(Supplier<Mono<?>> supplier) {
    supplier.get()
        .doOnError(this::log)
        .onErrorResume(throwable -> Mono.empty())
        .block();
  }

  protected T getEvent(ConsumerRecord<String, String> consumerRecord) {
    return jsonService.map(consumerRecord.value(), eventClass);
  }

  private void log(Throwable ex) {
    if (ex instanceof BaseException) {
      logger.debug("Base exception: {}", ex.getMessage());
    } else {
      logger.error("Undefined exception", ex);
    }
  }

  protected abstract Logger getLogger();
}
