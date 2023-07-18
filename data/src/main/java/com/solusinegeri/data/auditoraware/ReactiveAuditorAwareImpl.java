/*    */
package com.solusinegeri.data.auditoraware;
/*    */
/*    */

import com.solusinegeri.data.helper.SecurityHelper;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

import java.util.Optional;

/*    */
/*    */
/*    */
/*    */ public class ReactiveAuditorAwareImpl
    /*    */ implements ReactiveAuditorAware<String>
    /*    */ {

  /*    */
  @NonNull
  /*    */ public Mono<String> getCurrentAuditor() {
    /* 17 */
    return ReactiveSecurityContextHolder.getContext()
        /* 18 */.map(SecurityHelper::getPrincipal)
        /* 19 */.filter(Optional::isPresent)
        /* 20 */.map(Optional::get);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/auditoraware/ReactiveAuditorAwareImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */