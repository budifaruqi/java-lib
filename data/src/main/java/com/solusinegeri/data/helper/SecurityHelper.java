/*    */
package com.solusinegeri.data.helper;
/*    */
/*    */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Optional;

/*    */
/*    */
/*    */
/*    */
/*    */ public class SecurityHelper
    /*    */ {

  /*    */
  public static Optional<String> getPrincipal(SecurityContext securityContext) {
    /* 13 */
    return Optional.<Authentication>ofNullable(securityContext.getAuthentication())
        /* 14 */.map(Authentication::getPrincipal)
        /* 15 */.map(String::valueOf);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/helper/SecurityHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */