package com.solusinegeri.command.autoconfigure;

import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.command.reactive.executor.impl.CommandExecutorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;

import javax.validation.Validator;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveCommandAutoConfiguration {

  @Bean

  @ConditionalOnMissingBean
  public CommandExecutor commandExecutor(ApplicationContext applicationContext, Scheduler scheduler,
      Validator validator) {

    return (CommandExecutor) new CommandExecutorImpl(applicationContext, scheduler, validator);
  }
}

