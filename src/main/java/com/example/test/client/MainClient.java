package com.example.test.client;

import com.example.test.client.model.response.GetUserClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MainClient {

  Mono<List<GetUserClientResponse>> getUser();
}
