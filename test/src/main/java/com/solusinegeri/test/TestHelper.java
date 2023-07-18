/*    */
package com.solusinegeri.test;
/*    */
/*    */

import com.solusinegeri.session.model.Session;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public abstract class TestHelper
    /*    */ {

  /*    */   protected static final String SESSION_ID = "session-id";

  /*    */   protected static final String SESSION_USER_ID = "session-user-id";

  /*    */   protected Session session;

  /*    */
  /*    */
  protected Session initSession() {
    /* 18 */
    return Session.builder()
        /* 19 */.data(Collections.emptyMap())
        /* 20 */.id("session-id")
        /* 21 */.userId("session-user-id")
        /* 22 */.build();
    /*    */
  }

  /*    */
  /*    */
  @BeforeEach
  /*    */ protected void setUp() {
    /* 27 */
    this.session = initSession();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/TestHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */