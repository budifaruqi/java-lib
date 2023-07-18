/*    */
package com.solusinegeri.security.helper;
/*    */
/*    */

import com.solusinegeri.session.model.Session;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class SecurityHelper
    /*    */ {

  /* 14 */   public static final Session ANONYMOUS_SESSION = Session.builder()
      /* 15 */.data(Collections.emptyMap())
      /* 16 */.build();

  /*    */
  /*    */
  public static UsernamePasswordAuthenticationToken createAuthentication(Session session) {
    /* 19 */
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(session.getUserId(), null);
    /* 20 */
    token.setDetails(session);
    /* 21 */
    return token;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/helper/SecurityHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */