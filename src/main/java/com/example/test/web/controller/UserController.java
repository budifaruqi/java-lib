package com.example.test.web.controller;

import com.example.test.command.user.GetUserCommand;
import com.example.test.web.model.response.user.GetUserWebResponse;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/tes/user")
public class UserController extends BaseController {

  public UserController(CommandExecutor executor) {
    super(executor);
  }

  @GetMapping
  public Mono<Response<List<GetUserWebResponse>>> getUser() {
    return executor.execute(GetUserCommand.class, "tes")
        .map(ResponseHelper::ok);
  }
}
