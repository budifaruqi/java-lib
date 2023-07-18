/*    */
package com.solusinegeri.common.function;
/*    */
/*    */

import java.util.Optional;
import java.util.function.Supplier;

/*    */
/*    */
/*    */
/*    */
@FunctionalInterface
/*    */ public interface ThrowableSupplier<T>
    /*    */ {

  /*    */
  static <T> Supplier<T> apply(ThrowableSupplier<T> supplier) {
    /* 12 */
    return () -> {
      /*    */
      try {
        /*    */
        return supplier.apply();
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
  static <T> Supplier<T> apply(ThrowableSupplier<T> supplier, T defaultValue) {
    /* 22 */
    return () -> {
      /*    */
      try {
        /*    */
        return supplier.apply();
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
  static <T> Supplier<Optional<T>> applyOrEmpty(ThrowableSupplier<T> supplier) {
    /* 32 */
    return () -> Optional.of(applyOrNull(supplier))
        .map(Supplier::get);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  static <T> Supplier<T> applyOrNull(ThrowableSupplier<T> supplier) {
    /* 37 */
    return apply(supplier, null);
    /*    */
  }

  /*    */
  /*    */   T apply() throws Throwable;
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/function/ThrowableSupplier.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */