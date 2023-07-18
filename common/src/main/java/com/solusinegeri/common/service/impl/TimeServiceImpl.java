/*    */
package com.solusinegeri.common.service.impl;
/*    */
/*    */

import com.solusinegeri.common.service.TimeService;

import java.time.Instant;
import java.util.Date;

/*    */
/*    */
/*    */ public class TimeServiceImpl
    /*    */ implements TimeService
    /*    */ {

  /*    */
  public Date getCurrentTime() {
    /* 12 */
    return new Date();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public Instant getCurrentTimeInstant() {
    /* 17 */
    return Instant.now();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public long getCurrentTimeMillis() {
    /* 22 */
    return System.currentTimeMillis();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/service/impl/TimeServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */