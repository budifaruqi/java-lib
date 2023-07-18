/*    */
package com.solusinegeri.test.kafka.listener.servlet;
/*    */
/*    */

import com.solusinegeri.command.servlet.executor.CommandExecutor;
import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.test.TestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.mockito.Mock;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public abstract class KafkaListenerTestHelper
    /*    */ extends TestHelper
    /*    */ {

  /*    */   protected static final String EVENT_JSON = "{}";

  /*    */
  @Mock
  /*    */ protected ConsumerRecord<String, String> consumerRecord;

  /*    */
  @Mock
  /*    */ protected CommandExecutor executor;

  /*    */
  @Mock
  /*    */ protected JsonService jsonService;

  /*    */
  /*    */
  @BeforeEach
  /*    */ protected void setUp() {
    /* 29 */
    super.setUp();
    /* 30 */
    BDDMockito.given(this.consumerRecord.value())
        .willReturn("{}");
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/kafka/listener/servlet/KafkaListenerTestHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */