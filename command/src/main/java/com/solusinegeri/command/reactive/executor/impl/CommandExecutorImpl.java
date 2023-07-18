/*    */
package com.solusinegeri.command.reactive.executor.impl;

import com.solusinegeri.command.helper.CommandHelper;
import com.solusinegeri.command.reactive.Command;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.CollectionHelper;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.validation.Validator;
import java.util.Collections;
import java.util.List;

public class CommandExecutorImpl implements CommandExecutor {

  private final ApplicationContext applicationContext;

  private final Scheduler scheduler;

  private final Validator validator;

  public CommandExecutorImpl(ApplicationContext applicationContext, Scheduler scheduler, Validator validator) {
    this.applicationContext = applicationContext;
    this.scheduler = scheduler;
    this.validator = validator;
  }

  public <T, S> Mono<S> execute(Class<? extends Command<T, S>> commandClass, T request) {
    return this.execute(commandClass, request, Collections.emptyList());
  }

  public <T, S> Mono<S> execute(Class<? extends Command<T, S>> commandClass, T request, Class<?> validationGroup) {
    return this.execute(commandClass, request, CollectionHelper.toListOrEmpty(validationGroup));
  }

  public <T, S> Mono<S> execute(Class<? extends Command<T, S>> commandClass, T request,
      List<Class<?>> validationGroup) {
    return Mono.fromSupplier(() -> {
          return this.applicationContext.getBean(commandClass);
        })
        .publishOn(this.scheduler)
        .filterWhen((command) -> {
          return this.validateAndThrowIfInvalid(request, command, validationGroup);
        })
        .flatMap((command) -> {
          return command.execute(request);
        });
  }

  private <T, S> Mono<Boolean> validateAndThrowIfInvalid(T request, Command<T, S> command,
      List<Class<?>> validationGroup) {
    return Mono.fromSupplier(() -> {
          return command.getValidationGroup(request);
        })
        .map((group) -> {
          return CommandHelper.joinValidationGroup(validationGroup, group);
        })
        .map((group) -> {
          return this.validator.validate(request, group);
        })
        .filter(CollectionUtils::isNotEmpty)
        .handle((violations, sink) -> {
          sink.error(new ValidationException(violations));
        })
        .thenReturn(Boolean.TRUE);
  }
}


