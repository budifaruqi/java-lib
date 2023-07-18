/*    */
package com.solusinegeri.security.importfilter;
/*    */
/*    */

import com.solusinegeri.common.importfilter.CustomAutoConfigurationImportFilter;

import java.util.Set;

/*    */
/*    */ public class SecurityImportFilter
    /*    */ extends CustomAutoConfigurationImportFilter
    /*    */ {

  /*  9 */   private static final Set<String> FILTER = Set.of(
      "org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration",
      "org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration");

  /*    */
  /*    */
  /*    */
  /*    */
  public SecurityImportFilter() {
    /* 14 */
    super(FILTER);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/importfilter/SecurityImportFilter.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */