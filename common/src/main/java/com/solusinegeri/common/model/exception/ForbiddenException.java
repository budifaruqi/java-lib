/*   */
package com.solusinegeri.common.model.exception;
/*   */
/*   */

import org.springframework.http.HttpStatus;

/*   */
/*   */ public class ForbiddenException
    /*   */ extends BaseException
    /*   */ {

  /*   */
  public int getHttpStatus() {
    /* 9 */
    return HttpStatus.FORBIDDEN.value();
    /*   */
  }
  /*   */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/exception/ForbiddenException.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */