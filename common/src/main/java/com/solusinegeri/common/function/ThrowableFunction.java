/*    */
package com.solusinegeri.common.function;
/*    */
/*    */

import java.util.Optional;
import java.util.function.Function;

/*    */
/*    */
/*    */
/*    */
@FunctionalInterface
/*    */ public interface ThrowableFunction<T, R>
    /*    */ {

  /*    */
  static <T, R> Function<T, R> apply(ThrowableFunction<T, R> function) {
    /* 12 */
    return t -> {
      /*    */
      try {
        /*    */
        return function.apply(t);
        /* 15 */
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
  /*    */
  static <T, R> Function<T, R> apply(ThrowableFunction<T, R> function, R defaultValue) {
    /* 22 */
    return t -> {
      /*    */
      try {
        /*    */
        return function.apply(t);
        /* 25 */
      } catch (Throwable ex) {
        /*    */
        return defaultValue;
        /*    */
      }
      /*    */
    };
    /*    */
  }

  /*    */
  /*    */
  static <T, R> Function<T, Optional<R>> applyOrEmpty(ThrowableFunction<T, R> function) {
    /* 32 */
    return applyOrNull(function).andThen(Optional::ofNullable);
    /*    */
  }

  /*    */
  /*    */
  static <T, R> Function<T, R> applyOrNull(ThrowableFunction<T, R> function) {
    /* 36 */
    return apply(function, null);
    /*    */
  }

  /*    */
  /*    */   R apply(T paramT) throws Throwable;
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/function/ThrowableFunction.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */