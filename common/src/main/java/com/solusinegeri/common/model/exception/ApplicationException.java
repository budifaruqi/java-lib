/*    */
package com.solusinegeri.common.model.exception;

/*    */
/*    */ public class ApplicationException
    /*    */ extends RuntimeException
    /*    */ {

  /*    */
  public ApplicationException() {
  }

  /*    */
  /*    */
  public ApplicationException(String message) {
    /*  9 */
    super(message);
    /*    */
  }

  /*    */
  /*    */
  public ApplicationException(String message, Throwable cause) {
    /* 13 */
    super(message, cause);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/exception/ApplicationException.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */