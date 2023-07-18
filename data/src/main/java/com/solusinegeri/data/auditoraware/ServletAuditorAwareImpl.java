/*    */
package com.solusinegeri.data.auditoraware;
/*    */
/*    */

import com.solusinegeri.data.helper.SecurityHelper;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/*    */
/*    */
/*    */ public class ServletAuditorAwareImpl
    /*    */ implements AuditorAware<String>
    /*    */ {

  /*    */
  @NonNull
  /*    */ public Optional<String> getCurrentAuditor() {
    /* 16 */
    return Optional.<SecurityContext>of(SecurityContextHolder.getContext())
        /* 17 */.flatMap(SecurityHelper::getPrincipal);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/auditoraware/ServletAuditorAwareImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */