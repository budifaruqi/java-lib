/*    */
package com.solusinegeri.session.autoconfigure;
/*    */
/*    */

import com.solusinegeri.session.properties.SessionProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*    */
/*    */
/*    */
@Configuration
/*    */
@EnableConfigurationProperties({SessionProperties.class})
/*    */ public class SessionAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public PasswordEncoder passwordEncoder(SessionProperties sessionProperties) {
    /* 19 */
    return (PasswordEncoder) new BCryptPasswordEncoder(sessionProperties.getPasswordEncoderStrength());
    /*    */
  }
  /*    */
}