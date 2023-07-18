/*    */
package com.solusinegeri.command.autoconfigure;
/*    */
/*    */

import com.solusinegeri.command.servlet.executor.CommandExecutor;
import com.solusinegeri.command.servlet.executor.impl.CommandExecutorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

/*    */
/*    */
/*    */
/*    */
@Configuration
/*    */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/*    */ public class ServletCommandAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public CommandExecutor commandExecutor(ApplicationContext applicationContext, Validator validator) {
    /* 21 */
    return (CommandExecutor) new CommandExecutorImpl(applicationContext, validator);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-command/0.5.13/abinarystar-spring-command-0.5.13.jar!/com/abinarystar/spring/command/autoconfigure/ServletCommandAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */