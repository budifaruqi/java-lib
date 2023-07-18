/*    */
package com.solusinegeri.common.function;
/*    */
/*    */

import java.util.function.Consumer;

/*    */
/*    */
@FunctionalInterface
/*    */ public interface ThrowableConsumer<T>
    /*    */ {

  /*    */   void apply(T paramT) throws Throwable;

  /*    */
  /*    */
  static <T> Consumer<T> apply(ThrowableConsumer<T> consumer) {
    /* 11 */
    return t -> {
      /*    */
      try {
        /*    */
        consumer.apply(t);
        /* 14 */
      } catch (Throwable ex) {
        /*    */
        throw new RuntimeException(ex);
        /*    */
      }
      /*    */
    };
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/function/ThrowableConsumer.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */