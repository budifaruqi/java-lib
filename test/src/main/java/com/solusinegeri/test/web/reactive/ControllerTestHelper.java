package com.solusinegeri.test.web.reactive;

import com.solusinegeri.command.reactive.executor.CommandExecutor;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class ControllerTestHelper extends BaseControllerTestHelper {

  @MockBean
  protected CommandExecutor executor;
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/web/reactive/ControllerTestHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */