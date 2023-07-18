/*    */
package com.solusinegeri.security.autoconfigure;
/*    */
/*    */

import com.solusinegeri.security.configuration.ServletSecurityWebConfiguration;
import com.solusinegeri.security.filter.ServletSecurityFilter;
import com.solusinegeri.security.properties.SecurityProperties;
import com.solusinegeri.security.service.servlet.CookieService;
import com.solusinegeri.security.service.servlet.TokenService;
import com.solusinegeri.security.service.servlet.impl.AuthorizationCookieTokenService;
import com.solusinegeri.security.service.servlet.impl.AuthorizationHeaderTokenService;
import com.solusinegeri.security.service.servlet.impl.CookieServiceImpl;
import com.solusinegeri.security.validator.servlet.impl.AuthenticatedSessionValidator;
import com.solusinegeri.security.validator.servlet.impl.PermissionSessionValidator;
import com.solusinegeri.security.validator.servlet.impl.RoleSessionValidator;
import com.solusinegeri.session.service.servlet.SessionService;
import com.solusinegeri.session.service.servlet.UserService;
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
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/*    */
@Import({ServletSecurityWebConfiguration.class})
/*    */ public class ServletSecurityAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public AuthenticatedSessionValidator authenticatedSessionValidator() {
    /* 36 */
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
    /* 45 */
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
    /* 54 */
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
    /* 63 */
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
    /* 70 */
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
    /* 77 */
    return new RoleSessionValidator(userService);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public ServletSecurityFilter securityFilter(SessionService sessionService,
      List<TokenService> tokenServices) {
    /* 83 */
    return new ServletSecurityFilter(sessionService, tokenServices);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/autoconfigure/ServletSecurityAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */