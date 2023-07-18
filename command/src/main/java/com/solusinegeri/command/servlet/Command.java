/*    */
package com.solusinegeri.command.servlet;
/*    */
/*    */

import java.util.Collections;
import java.util.List;

/*    */
/*    */ public interface Command<T, S>
    /*    */ {

  /*    */   S execute(T paramT);

  /*    */
  /*    */
  default List<Class<?>> getValidationGroup(T request) {
    /* 11 */
    return Collections.emptyList();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-command/0.5.13/abinarystar-spring-command-0.5.13.jar!/com/abinarystar/spring/command/servlet/Command.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */