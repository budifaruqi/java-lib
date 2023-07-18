/*    */
package com.solusinegeri.web.advice;
/*    */
/*    */

import com.solusinegeri.common.model.exception.ApplicationException;
import com.solusinegeri.common.model.exception.BaseException;
import com.solusinegeri.common.model.web.response.Response;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/*    */
/*    */
/*    */ public abstract class BaseExceptionControllerAdvice
    /*    */ {

  /*    */
  protected abstract Logger getLogger();

  /*    */
  /*    */
  @ExceptionHandler
  /*    */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  /*    */ public void applicationException(ApplicationException ex) {
    /* 20 */
    getLogger().error("Application exception", (Throwable) ex);
    /*    */
  }

  /*    */
  /*    */
  @ExceptionHandler
  /*    */ public ResponseEntity<Response<?>> baseException(BaseException ex) {
    /* 25 */
    getLogger().debug("Runtime exception: {}", ex.getMessage());
    /* 26 */
    return new ResponseEntity(ex.getResponse(), HttpStatus.valueOf(ex.getHttpStatus()));
    /*    */
  }

  /*    */
  /*    */
  @ExceptionHandler
  /*    */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  /*    */ public void throwable(Throwable ex) {
    /* 32 */
    getLogger().error("Undefined exception", ex);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/advice/BaseExceptionControllerAdvice.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */