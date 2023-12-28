package com.example.test.web.controller;

import com.example.test.client.MembershipClient;
import com.example.test.command.CreateCompanyCommand;
import com.example.test.command.user.GetUserCommand;
import com.example.test.web.model.request.CreateCompanyWebRequest;
import com.example.test.web.model.response.user.GetUserWebResponse;
import com.solusinegeri.command.reactive.executor.CommandExecutor;
import com.solusinegeri.common.helper.ResponseHelper;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.web.controller.reactive.BaseController;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RestController
@RequestMapping("/tes/user")
//@MustAuthenticated(userRole = {RoleEnum.sa})
public class UserController extends BaseController {

  public UserController(CommandExecutor executor, MembershipClient membershipClient) {
    super(executor);
    this.membershipClient = membershipClient;
  }

  private final MembershipClient membershipClient;

  @GetMapping
  public Mono<Response<List<GetUserWebResponse>>> getUser() {
    return executor.execute(GetUserCommand.class, "tes")
        .map(ResponseHelper::ok);
  }

  @PostMapping
  public Mono<Response<Object>> createCompany(@RequestBody CreateCompanyWebRequest request) {
    assertEquals("5.1.10.RELEASE", SpringVersion.getVersion());
    return executor.execute(CreateCompanyCommand.class, request.getName())
        .map(ResponseHelper::ok);
  }

  @GetMapping("/api/ping")
  public String getPing() {
    return "OK";
  }
  
  //  @GetMapping("/test")
  //  public Mono<Response<Object>> test() {
  //    return executor.execute(TestAuthCommand.class, "")
  //        .map(ResponseHelper::ok);
  //  }
}
