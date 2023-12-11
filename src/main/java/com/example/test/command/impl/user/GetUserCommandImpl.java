package com.example.test.command.impl.user;

import com.example.test.client.MainClient;
import com.example.test.client.model.response.GetUserClientResponse;
import com.example.test.command.user.GetUserCommand;
import com.example.test.web.model.response.user.GetUserWebResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetUserCommandImpl implements GetUserCommand {

  private final MainClient mainClient;

  public GetUserCommandImpl(MainClient mainClient) {
    this.mainClient = mainClient;
  }

  @Override
  public Mono<List<GetUserWebResponse>> execute(String request) {
    return Mono.defer(this::getUser)
        .flatMap(listUser -> Flux.fromIterable(listUser)
            .map(this::toGetWebResponse)
            .collectList());
  }

  private Mono<List<GetUserClientResponse>> getUser() {
    return mainClient.getUser();
  }

  private GetUserWebResponse toGetWebResponse(GetUserClientResponse user) {
    return GetUserWebResponse.builder()
        .name(user.getName())
        .email(user.getEmail())
        .street(user.getAddress()
            .getStreet())
        .city(user.getAddress()
            .getCity())
        .build();
  }
}
