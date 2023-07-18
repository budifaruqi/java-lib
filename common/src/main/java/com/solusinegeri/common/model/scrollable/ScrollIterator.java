/*    */
package com.solusinegeri.common.model.scrollable;
/*    */
/*    */

import java.util.Iterator;
import java.util.Optional;

/*    */
/*    */ public abstract class ScrollIterator<E>
    /*    */ implements Iterator<E> {

  /*    */   private Iterator<E> currentIterator;

  /*    */
  /*    */
  protected ScrollIterator(Iterator<E> currentIterator) {
    /* 11 */
    this.currentIterator = currentIterator;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected abstract Optional<Iterator<E>> nextIterator();

  /*    */
  /*    */
  public boolean hasNext() {
    /* 18 */
    return (this.currentIterator.hasNext() || hasNextIterator());
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public E next() {
    /* 23 */
    return this.currentIterator.next();
    /*    */
  }

  /*    */
  /*    */
  private boolean hasNextIterator() {
    /* 27 */
    Optional<Iterator<E>> nextIterator = nextIterator();
    /* 28 */
    nextIterator.ifPresent(iterator -> this.currentIterator = iterator);
    /* 29 */
    return nextIterator.isPresent();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/scrollable/ScrollIterator.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */