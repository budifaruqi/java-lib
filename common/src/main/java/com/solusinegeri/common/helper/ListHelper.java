/*    */
package com.solusinegeri.common.helper;
/*    */
/*    */

import java.util.List;
import java.util.Optional;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class ListHelper
    /*    */ {

  /*    */
  public static <T> T getFirst(List<T> list) {
    /* 13 */
    return getFirst(list, null);
    /*    */
  }

  /*    */
  /*    */
  public static <T> T getFirst(List<T> list, T defaultValue) {
    /* 17 */
    return CollectionHelper.getFirst(list, defaultValue);
    /*    */
  }

  /*    */
  /*    */
  public static <T> T getLast(List<T> list) {
    /* 21 */
    return getLast(list, null);
    /*    */
  }

  /*    */
  /*    */
  public static <T> T getLast(List<T> list, T defaultValue) {
    /* 25 */
    return Optional.<List<T>>ofNullable(list)
        /* 26 */.filter(l -> !l.isEmpty())
        /* 27 */.map(l -> l.get(l.size() - 1))
        /* 28 */.orElse(defaultValue);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/helper/ListHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */