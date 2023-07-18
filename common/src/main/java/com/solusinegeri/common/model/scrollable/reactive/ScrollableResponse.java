/*    */
package com.solusinegeri.common.model.scrollable.reactive;
/*    */
/*    */

import com.solusinegeri.common.model.scrollable.ScrollIterable;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class ScrollableResponse<DATA, NEXT_KEY, VALUE>
    /*    */ extends ScrollIterable<VALUE>
    /*    */ {

  /*    */   private final Function<NEXT_KEY, Mono<DATA>> getNextDataFunction;

  /*    */   private final Function<DATA, NEXT_KEY> getNextKeyFunction;

  /*    */   private final Function<DATA, Iterable<VALUE>> getValueFunction;

  /*    */   private DATA data;

  /*    */
  /*    */
  private ScrollableResponse(Function<NEXT_KEY, Mono<DATA>> getNextDataFunction,
      Function<DATA, NEXT_KEY> getNextKeyFunction, Function<DATA, Iterable<VALUE>> getValueFunction,
      Mono<DATA> firstData) {
    /* 23 */
    super(getValueFunction.apply((DATA) firstData.block()));
    /* 24 */
    this.getNextDataFunction = getNextDataFunction;
    /* 25 */
    this.getNextKeyFunction = getNextKeyFunction;
    /* 26 */
    this.getValueFunction = getValueFunction;
    /* 27 */
    this.data = (DATA) firstData.block();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  public static <DATA, NEXT_KEY, VALUE> ScrollableResponse<DATA, NEXT_KEY, VALUE> scroll(
      Function<NEXT_KEY, Mono<DATA>> getNextDataFunction, Function<DATA, NEXT_KEY> getNextKeyFunction,
      Function<DATA, Iterable<VALUE>> getValueFunction, Mono<DATA> firstData) {
    /* 33 */
    return new ScrollableResponse<>(getNextDataFunction, getNextKeyFunction, getValueFunction, firstData);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Optional<Iterable<VALUE>> nextIterable() {
    /* 38 */
    this
        /*    */
        /*    */
        /*    */
        /* 42 */.data = Optional.<DATA>ofNullable(this.data)
        .<NEXT_KEY>map(this.getNextKeyFunction)
        .<Mono<DATA>>map(this.getNextDataFunction)
        .map(Mono::block)
        .orElse(null);
    /* 43 */
    return Optional.<DATA>ofNullable(this.data)
        /* 44 */.map(this.getValueFunction);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/scrollable/reactive/ScrollableResponse.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */