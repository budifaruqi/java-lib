/*    */
package com.solusinegeri.web.controller.servlet;
/*    */
/*    */

import com.solusinegeri.command.servlet.executor.CommandExecutor;

/*    */
/*    */ public abstract class BaseController
    /*    */ {

  /*    */   protected final CommandExecutor executor;

  /*    */
  /*    */
  public BaseController(CommandExecutor executor) {
    /* 10 */
    this.executor = executor;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/controller/servlet/BaseController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */