/*    */
package com.solusinegeri.security.autoconfigure;
/*    */
/*    */

import com.solusinegeri.security.configuration.ReactiveSecurityWebConfiguration;
import com.solusinegeri.security.filter.ReactiveSecurityFilter;
import com.solusinegeri.security.properties.SecurityProperties;
import com.solusinegeri.security.service.reactive.CookieService;
import com.solusinegeri.security.service.reactive.TokenService;
import com.solusinegeri.security.service.reactive.impl.AuthorizationCookieTokenService;
import com.solusinegeri.security.service.reactive.impl.AuthorizationHeaderTokenService;
import com.solusinegeri.security.service.reactive.impl.CookieServiceImpl;
import com.solusinegeri.security.validator.reactive.impl.AuthenticatedSessionValidator;
import com.solusinegeri.security.validator.reactive.impl.PermissionSessionValidator;
import com.solusinegeri.security.validator.reactive.impl.RoleSessionValidator;
import com.solusinegeri.session.autoconfigure.ReactiveSessionAutoConfiguration;
import com.solusinegeri.session.service.reactive.SessionService;
import com.solusinegeri.session.service.reactive.UserService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/*    */
/*    */
/*    */
/*    */
/*    */
@Configuration
/*    */
@AutoConfigureAfter({ReactiveSessionAutoConfiguration.class})
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
/*    */
@Import({ReactiveSecurityWebConfiguration.class})
/*    */ public class ReactiveSecurityAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public AuthenticatedSessionValidator authenticatedSessionValidator() {
    /* 39 */
    return new AuthenticatedSessionValidator();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */
  @ConditionalOnProperty(name = {"abinarystar.security.token-source"}, havingValue = "cookie", matchIfMissing = true)
  /*    */ public AuthorizationCookieTokenService authorizationCookieTokenService(
      SecurityProperties securityProperties) {
    /* 48 */
    return new AuthorizationCookieTokenService(securityProperties);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */
  @ConditionalOnProperty(name = {"abinarystar.security.token-source"}, havingValue = "header", matchIfMissing = true)
  /*    */ public AuthorizationHeaderTokenService authorizationHeaderTokenService() {
    /* 57 */
    return new AuthorizationHeaderTokenService();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */
  @ConditionalOnProperty(name = {"abinarystar.security.token-source"}, havingValue = "cookie", matchIfMissing = true)
  /*    */ public CookieService cookieService(SecurityProperties securityProperties) {
    /* 66 */
    return (CookieService) new CookieServiceImpl(securityProperties);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnBean({UserService.class})
  /*    */
  @ConditionalOnMissingBean
  /*    */ public PermissionSessionValidator permissionSessionValidator(UserService userService) {
    /* 73 */
    return new PermissionSessionValidator(userService);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnBean({UserService.class})
  /*    */
  @ConditionalOnMissingBean
  /*    */ public RoleSessionValidator roleSessionValidator(UserService userService) {
    /* 80 */
    return new RoleSessionValidator(userService);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public ReactiveSecurityFilter securityFilter(SessionService sessionService,
      List<TokenService> tokenServices) {
    /* 86 */
    return new ReactiveSecurityFilter(sessionService, tokenServices);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/autoconfigure/ReactiveSecurityAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */