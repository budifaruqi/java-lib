package com.solusinegeri.command.reactive.executor;

import com.solusinegeri.command.reactive.Command;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CommandExecutor {

  <T, S> Mono<S> execute(Class<? extends Command<T, S>> paramClass, T paramT);

  <T, S> Mono<S> execute(Class<? extends Command<T, S>> paramClass, T paramT, Class<?> paramClass1);

  <T, S> Mono<S> execute(Class<? extends Command<T, S>> paramClass, T paramT, List<Class<?>> paramList);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-command/0.5.13/abinarystar-spring-command-0.5.13.jar!/com/abinarystar/spring/command/reactive/executor/CommandExecutor.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */