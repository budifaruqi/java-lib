package com.example.test.client.impl;

import com.example.test.client.MainClient;
import com.example.test.client.model.response.GetUserClientResponse;
import com.solusinegeri.common.service.JsonService;
import com.solusinegeri.validation.model.exception.ValidationException;
import com.solusinegeri.web_client.properties.WebClientProperties;
import com.solusinegeri.web_client.reactive.BaseWebClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
@Slf4j
public class MainClientImpl extends BaseWebClient implements MainClient {

    private static final String configName = "main";

    private final JsonService jsonService;

    public MainClientImpl(WebClientProperties clientProperties, JsonService jsonService) {
        super(clientProperties, configName);
        this.jsonService = jsonService;
    }

    @Override
    public Mono<List<GetUserClientResponse>> getUser() {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/users")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, toErrorValidationClientResponse())
                .bodyToMono(new ParameterizedTypeReference<List<GetUserClientResponse>>() {
                });
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    private Function<ClientResponse, Mono<? extends Throwable>> toErrorValidationClientResponse() {
        return clientResponse -> clientResponse.bodyToMono(Object.class)
                .filter(Objects::nonNull)
                .doOnNext(r -> log.error(jsonService.map(r)))
                .map(errorResponse -> new ValidationException(errorResponse.toString()));
    }
}
