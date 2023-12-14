/*    */
package com.solusinegeri.validation.model.exception;
/*    */
/*    */

import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.exception.BaseException;
import com.solusinegeri.validation.helper.ErrorHelper;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*    */
/*    */
/*    */ public class ValidationException
    /*    */ extends BaseException
    /*    */ {

  /*    */
  public ValidationException() {
    /* 18 */
    this((Object) null, Collections.emptyList(), Collections.emptyMap());
    /*    */
  }

  /*    */
  /*    */
  public ValidationException(String errorCode) {
    /* 22 */
    this((Object) null, Collections.singletonList(errorCode), Collections.emptyMap());
    /*    */
  }

  /*    */
  /*    */
  public ValidationException(String key, String message) {
    /* 26 */
    this((Object) null, Collections.emptyList(), Collections.singletonMap(key, Collections.singletonList(message)));
    /*    */
  }

  /*    */
  /*    */
  public ValidationException(String errorCode, String key, String message) {
    /* 30 */
    this((Object) null, Collections.singletonList(errorCode),
        Collections.singletonMap(key, Collections.singletonList(message)));
    /*    */
  }

  /*    */
  /*    */
  public <T> ValidationException(Set<ConstraintViolation<T>> violations) {
    /* 34 */
    this((Object) null, ErrorHelper.getCodes(violations), ErrorHelper.getDetail(violations));
    /*    */
  }

  /*    */
  /*    */
  public ValidationException(Object body, List<String> errorCodes, Map<String, List<String>> errors) {
    /* 38 */
    super(ResponseHelper.error(body, errorCodes, errors));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public int getHttpStatus() {
    /* 43 */
    return HttpStatus.BAD_REQUEST.value();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-validation/0.5.13/abinarystar-spring-validation-0.5.13.jar!/com/abinarystar/spring/validation/model/exception/ValidationException.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */