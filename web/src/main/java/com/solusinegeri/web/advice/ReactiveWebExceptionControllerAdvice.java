/*    */
package com.solusinegeri.web.advice;
/*    */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

/*    */
/*    */
@RestControllerAdvice
/*    */ public class ReactiveWebExceptionControllerAdvice extends BaseExceptionControllerAdvice {

  /* 12 */   private static final Logger log = LoggerFactory.getLogger(ReactiveWebExceptionControllerAdvice.class);

  /*    */
  /*    */
  /*    */
  @ExceptionHandler
  /*    */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  /*    */ public void serverWebInputException(ServerWebInputException ex) {
    /* 18 */
    log.debug("Server web input exception: {}", ex.getMessage());
    /*    */
  }

  /*    */
  /*    */
  /*    */
  protected Logger getLogger() {
    /* 23 */
    return log;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/advice/ReactiveWebExceptionControllerAdvice.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */