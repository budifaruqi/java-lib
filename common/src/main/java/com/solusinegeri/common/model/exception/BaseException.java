/*    */
package com.solusinegeri.common.model.exception;
/*    */
/*    */

import com.solusinegeri.common.model.web.response.Response;

/*    */
/*    */ public abstract class BaseException
    /*    */ extends RuntimeException {

  /*    */   private Response<?> response;

  /*    */
  /*    */
  public BaseException() {
  }

  /*    */
  /*    */
  public Response<?> getResponse() {
    /* 12 */
    return this.response;
  }

  public void setResponse(Response<?> response) {
    /* 13 */
    this.response = response;
    /*    */
  }

  /*    */
  /*    */
  public BaseException(Response<?> response) {
    /* 17 */
    super(response.toString());
    /* 18 */
    this.response = response;
    /*    */
  }

  /*    */
  /*    */
  public BaseException(String message) {
    /* 22 */
    super(message);
    /*    */
  }

  /*    */
  /*    */
  public BaseException(Throwable ex) {
    /* 26 */
    super(ex);
    /*    */
  }

  /*    */
  /*    */
  public BaseException(String message, Throwable ex) {
    /* 30 */
    super(message, ex);
    /*    */
  }

  /*    */
  /*    */
  public abstract int getHttpStatus();
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/exception/BaseException.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */