/*    */
package com.solusinegeri.common.model.exception;
/*    */
/*    */

import com.solusinegeri.common.helper.ResponseHelper;
import org.springframework.http.HttpStatus;

import java.util.Collections;

/*    */
/*    */
/*    */ public class UnauthorizedException
    /*    */ extends BaseException
    /*    */ {

  /*    */
  public UnauthorizedException() {
  }

  /*    */
  /*    */
  public UnauthorizedException(String errorCode) {
    /* 14 */
    super(ResponseHelper.error(null, Collections.singletonList(errorCode), Collections.emptyMap()));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getHttpStatus() {
    /* 19 */
    return HttpStatus.UNAUTHORIZED.value();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/model/exception/UnauthorizedException.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */