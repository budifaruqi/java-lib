/*    */
package com.solusinegeri.kafka.listener.servlet;
/*    */
/*    */

import com.solusinegeri.common.model.exception.BaseException;
import com.solusinegeri.common.service.JsonService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;

import java.util.function.Supplier;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public abstract class BaseKafkaListener<T>
    /*    */ {

  /*    */   protected final JsonService jsonService;

  /*    */   private final Class<T> eventClass;

  /*    */
  /*    */
  public BaseKafkaListener(JsonService jsonService, Class<T> eventClass) {
    /* 18 */
    this.jsonService = jsonService;
    /* 19 */
    this.eventClass = eventClass;
    /*    */
  }

  /*    */
  /*    */
  protected abstract Logger getLogger();

  /*    */
  /*    */
  protected void execute(Supplier<?> supplier) {
    /*    */
    try {
      /* 26 */
      supplier.get();
      /* 27 */
    } catch (Throwable ex) {
      /* 28 */
      log(ex);
      /*    */
    }
    /*    */
  }

  /*    */
  /*    */
  protected T getEvent(ConsumerRecord<String, String> consumerRecord) {
    /* 33 */
    return (T) this.jsonService.map((String) consumerRecord.value(), this.eventClass);
    /*    */
  }

  /*    */
  /*    */
  private void log(Throwable ex) {
    /* 37 */
    if (ex instanceof BaseException) {
      /* 38 */
      getLogger().debug("Base exception: {}", ex.getMessage());
      /*    */
    } else {
      /* 40 */
      getLogger().error("Undefined exception", ex);
      /*    */
    }
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-kafka/0.5.13/abinarystar-spring-kafka-0.5.13.jar!/com/abinarystar/spring/kafka/listener/servlet/BaseKafkaListener.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */