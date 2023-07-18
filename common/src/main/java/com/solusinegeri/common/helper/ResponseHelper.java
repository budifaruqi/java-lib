/*    */
package com.solusinegeri.common.helper;
/*    */
/*    */

import com.solusinegeri.common.model.web.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class ResponseHelper
    /*    */ {

  /*    */
  public static <T> Response<T> error(T data, List<String> errorCodes, Map<String, List<String>> errors) {
    /* 17 */
    return Response.<T>builder()
        /* 18 */.data(data)
        /* 19 */.errorCodes(errorCodes)
        /* 20 */.errors(errors)
        /* 21 */.build();
    /*    */
  }

  /*    */
  /*    */
  public static <T> Response<T> ok() {
    /* 25 */
    return new Response();
    /*    */
  }

  /*    */
  /*    */
  public static <T> Response<T> ok(T data) {
    /* 29 */
    return Response.<T>builder()
        /* 30 */.data(data)
        /* 31 */.build();
    /*    */
  }

  /*    */
  /*    */
  public static <T> Response<List<T>> ok(@NonNull Page<T> data) {
    /* 35 */
    return Response.<List<T>>builder()
        /* 36 */.data(data.getContent())
        /* 37 */.paging(toPaging(data))
        /* 38 */.build();
    /*    */
  }

  /*    */
  /*    */
  private static <T> Response.Paging toPaging(@NonNull Page<T> data) {
    /* 42 */
    return Response.Paging.builder()
        /* 43 */.page(data.getNumber() + 1)
        /* 44 */.size(data.getSize())
        /* 45 */.totalElements(data.getTotalElements())
        /* 46 */.totalPages(data.getTotalPages())
        /* 47 */.build();
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/helper/ResponseHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */