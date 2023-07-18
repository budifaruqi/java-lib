/*    */
package com.solusinegeri.common.service.impl;
/*    */
/*    */

import com.solusinegeri.common.service.UuidService;

import java.util.UUID;

/*    */
/*    */
/*    */ public class UuidServiceImpl
    /*    */ implements UuidService
    /*    */ {

  /*    */
  public String generate() {
    /* 11 */
    return UUID.randomUUID()
        /* 12 */.toString();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/service/impl/UuidServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */