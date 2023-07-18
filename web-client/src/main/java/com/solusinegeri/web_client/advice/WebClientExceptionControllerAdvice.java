/*    */
package com.solusinegeri.web_client.advice;
/*    */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/*    */
/*    */
@RestControllerAdvice
/*    */
@Order(-2147483648)
/*    */ public class WebClientExceptionControllerAdvice {

  /* 14 */   private static final Logger log = LoggerFactory.getLogger(WebClientExceptionControllerAdvice.class);

  /*    */
  /*    */
  /*    */
  @ExceptionHandler
  /*    */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  /*    */ public void webClientResponseException(WebClientResponseException ex) {
    /* 20 */
    log.error("Web client response exception: {}", ex.getResponseBodyAsString());
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web-client/0.5.13/abinarystar-spring-web-client-0.5.13.jar!/com/abinarystar/spring/web/client/advice/WebClientExceptionControllerAdvice.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */