/*    */
package com.solusinegeri.data.advice;
/*    */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*    */
/*    */
@RestControllerAdvice
/*    */
@Order(-2147483648)
/*    */ public class DataExceptionControllerAdvice {

  /* 14 */   private static final Logger log = LoggerFactory.getLogger(DataExceptionControllerAdvice.class);

  /*    */
  /*    */
  /*    */
  @ExceptionHandler
  /*    */
  @ResponseStatus(HttpStatus.CONFLICT)
  /*    */ public void optimisticLockingFailureException(OptimisticLockingFailureException ex) {
    /* 20 */
    log.warn("Optimistic locking failure exception: {}", ex.getMessage());
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/advice/DataExceptionControllerAdvice.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */