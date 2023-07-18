/*    */
package com.solusinegeri.common.model.scrollable;
/*    */
/*    */

import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.Optional;

/*    */
/*    */
/*    */ public abstract class ScrollIterable<E>
    /*    */ implements Iterable<E>
    /*    */ {

  /*    */   private final Iterable<E> firstIterable;

  /*    */   private final ScrollIterator<E> scrollIterator;

  /*    */
  /*    */
  protected ScrollIterable(Iterable<E> firstIterable) {
    /* 15 */
    this.firstIterable = firstIterable;
    /* 16 */
    this.scrollIterator = createScrollIterator();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected abstract Optional<Iterable<E>> nextIterable();

  /*    */
  /*    */
  @NonNull
  /*    */ public Iterator<E> iterator() {
    /* 24 */
    return this.scrollIterator;
    /*    */
  }

  /*    */
  /*    */
  private ScrollIterator<E> createScrollIterator() {
    /* 28 */
    return new ScrollIterator<E>(this.firstIterable.iterator())
        /*    */ {
      /*    */
      protected Optional<Iterator<E>> nextIterator() {
        /* 31 */
        return ScrollIterable.this.nextIterable()
            .map(Iterable::iterator);
        /*    */
      }
      /*    */
    };
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/scrollable/ScrollIterable.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */