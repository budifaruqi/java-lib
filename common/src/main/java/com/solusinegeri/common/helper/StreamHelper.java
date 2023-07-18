/*    */
package com.solusinegeri.common.helper;
/*    */
/*    */

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class StreamHelper
    /*    */ {

  /*    */
  public static <T> Stream<T> toStream(Iterable<T> iterable) {
    /* 13 */
    return StreamSupport.stream(iterable.spliterator(), false);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/helper/StreamHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */