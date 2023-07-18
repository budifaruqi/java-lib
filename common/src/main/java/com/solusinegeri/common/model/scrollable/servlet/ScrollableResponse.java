/*    */
package com.solusinegeri.common.model.scrollable.servlet;
/*    */
/*    */

import com.solusinegeri.common.model.scrollable.ScrollIterable;

import java.util.Optional;
import java.util.function.Function;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class ScrollableResponse<DATA, NEXT_KEY, VALUE>
    /*    */ extends ScrollIterable<VALUE>
    /*    */ {

  /*    */   private final Function<NEXT_KEY, DATA> getNextDataFunction;

  /*    */   private final Function<DATA, NEXT_KEY> getNextKeyFunction;

  /*    */   private final Function<DATA, Iterable<VALUE>> getValueFunction;

  /*    */   private DATA data;

  /*    */
  /*    */
  private ScrollableResponse(Function<NEXT_KEY, DATA> getNextDataFunction, Function<DATA, NEXT_KEY> getNextKeyFunction,
      Function<DATA, Iterable<VALUE>> getValueFunction, DATA firstData) {
    /* 20 */
    super(getValueFunction.apply(firstData));
    /* 21 */
    this.getNextDataFunction = getNextDataFunction;
    /* 22 */
    this.getNextKeyFunction = getNextKeyFunction;
    /* 23 */
    this.getValueFunction = getValueFunction;
    /* 24 */
    this.data = firstData;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  public static <DATA, NEXT_KEY, VALUE> ScrollableResponse<DATA, NEXT_KEY, VALUE> scroll(
      Function<NEXT_KEY, DATA> getNextDataFunction, Function<DATA, NEXT_KEY> getNextKeyFunction,
      Function<DATA, Iterable<VALUE>> getValueFunction, DATA firstData) {
    /* 30 */
    return new ScrollableResponse<>(getNextDataFunction, getNextKeyFunction, getValueFunction, firstData);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Optional<Iterable<VALUE>> nextIterable() {
    /* 35 */
    this
        /*    */
        /*    */
        /* 38 */.data = Optional.<DATA>ofNullable(this.data)
        .<NEXT_KEY>map(this.getNextKeyFunction)
        .<DATA>map(this.getNextDataFunction)
        .orElse(null);
    /* 39 */
    return Optional.<DATA>ofNullable(this.data)
        /* 40 */.map(this.getValueFunction);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/scrollable/servlet/ScrollableResponse.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */