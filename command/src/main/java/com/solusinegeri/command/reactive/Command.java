/*    */
package com.solusinegeri.command.reactive;
/*    */
/*    */

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/*    */
/*    */
/*    */ public interface Command<T, S>
    /*    */ {

  /*    */   Mono<S> execute(T paramT);

  /*    */
  /*    */
  default List<Class<?>> getValidationGroup(T request) {
    /* 13 */
    return Collections.emptyList();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-command/0.5.13/abinarystar-spring-command-0.5.13.jar!/com/abinarystar/spring/command/reactive/Command.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */