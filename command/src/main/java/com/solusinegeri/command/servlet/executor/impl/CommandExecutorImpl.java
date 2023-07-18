/*    */
package com.solusinegeri.command.servlet.executor.impl;
/*    */
/*    */

import com.solusinegeri.command.helper.CommandHelper;
import com.solusinegeri.command.servlet.Command;
import com.solusinegeri.command.servlet.executor.CommandExecutor;
import com.solusinegeri.common.helper.CollectionHelper;
import com.solusinegeri.validation.model.exception.ValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/*    */
/*    */
/*    */
/*    */ public class CommandExecutorImpl
    /*    */ implements CommandExecutor
    /*    */ {

  /*    */   private final ApplicationContext applicationContext;

  /*    */   private final Validator validator;

  /*    */
  /*    */
  public CommandExecutorImpl(ApplicationContext applicationContext, Validator validator) {
    /* 25 */
    this.applicationContext = applicationContext;
    /* 26 */
    this.validator = validator;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public <T, S> S execute(Class<? extends Command<T, S>> commandClass, T request) {
    /* 31 */
    return execute(commandClass, request, Collections.emptyList());
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public <T, S> S execute(Class<? extends Command<T, S>> commandClass, T request, Class<?> validationGroup) {
    /* 36 */
    return execute(commandClass, request, CollectionHelper.toListOrEmpty(validationGroup));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public <T, S> S execute(Class<? extends Command<T, S>> commandClass, T request, List<Class<?>> validationGroup) {
    /* 41 */
    Command<T, S> command = (Command<T, S>) this.applicationContext.getBean(commandClass);
    /* 42 */
    validateAndThrowIfInvalid(request, command, validationGroup);
    /* 43 */
    return (S) command.execute(request);
    /*    */
  }

  /*    */
  /*    */
  private <T, S> void validateAndThrowIfInvalid(T request, Command<T, S> command, List<Class<?>> validationGroup) {
    /* 47 */
    Class<?>[] group = CommandHelper.joinValidationGroup(validationGroup, command.getValidationGroup(request));
    /* 48 */
    Set<ConstraintViolation<Object>> violations = this.validator.validate(request, group);
    /* 49 */
    if (CollectionUtils.isNotEmpty(violations))
      /* 50 */ {
      throw new ValidationException(violations);
    }
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-command/0.5.13/abinarystar-spring-command-0.5.13.jar!/com/abinarystar/spring/command/servlet/executor/impl/CommandExecutorImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */