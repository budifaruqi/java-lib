/*     */
package com.solusinegeri.web_client.reactive;
/*     */
/*     */

import com.solusinegeri.common.model.exception.ForbiddenException;
import com.solusinegeri.common.model.exception.UnauthorizedException;
import com.solusinegeri.common.model.web.response.Response;
import com.solusinegeri.validation.model.exception.ValidationException;
import com.solusinegeri.web_client.properties.WebClientProperties;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

/*     */
/*     */ public abstract class BaseWebClient
    /*     */ implements InitializingBean
    /*     */ {

  /*     */   private final WebClientProperties clientProperties;

  /*     */   private final String configName;

  /*     */   protected WebClient client;

  /*     */   protected WebClientProperties.ClientConfig clientConfig;

  /*     */
  /*     */
  public BaseWebClient(WebClientProperties clientProperties, String configName) {
    /*  39 */
    this.clientProperties = clientProperties;
    /*  40 */
    this.configName = configName;
    /*     */
  }

  /*     */
  /*     */
  /*     */
  /*     */
  protected static Function<ClientResponse, Mono<? extends Throwable>> throw4xxException() {
    /*  46 */
    return (clientResponse) -> {
      HttpStatus httpStatus = HttpStatus.valueOf(clientResponse.statusCode()
          .value());
      if (httpStatus == HttpStatus.BAD_REQUEST) {
        return clientResponse.bodyToMono(new ParameterizedTypeReference<Response<Object>>() {})
            .map((response) -> {
              return new ValidationException(response.getData(), response.getErrorCodes(), response.getErrors());
            })
            .switchIfEmpty(Mono.defer(() -> {
              return Mono.just(new ValidationException());
            }));
      } else if (httpStatus == HttpStatus.UNAUTHORIZED) {
        return clientResponse.toBodilessEntity()
            .map((responseEntity) -> {
              return new UnauthorizedException();
            });
      } else {
        return httpStatus == HttpStatus.FORBIDDEN ? clientResponse.toBodilessEntity()
            .map((responseEntity) -> {
              return new ForbiddenException();
            }) : clientResponse.createException();
      }
    };
    /*     */
  }

  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  /*     */
  public void afterPropertiesSet() {
    /*  67 */
    setClientConfig();
    /*  68 */
    setClient();
    /*     */
  }

  /*     */
  /*     */
  protected Consumer<WebClient.Builder> createClient() {
    /*  72 */
    return builder -> builder.baseUrl(
            StringUtils.join((Object[]) new String[]{this.clientConfig.getHostName(), this.clientConfig.getBasePath()}))
        .clientConnector((ClientHttpConnector) new ReactorClientHttpConnector(createHttpClient(this.clientConfig)));
    /*     */
  }

  /*     */
  /*     */
  /*     */
  protected HttpClient createHttpClient(WebClientProperties.ClientConfig config) {
    /*  77 */
    Duration connectionTimeout = config.getConnectionTimeout();
    /*  78 */
    Duration readTimeout = config.getReadTimeout();
    /*  79 */
    Duration writeTimeout = config.getWriteTimeout();
    /*  80 */
    return ((HttpClient) ((HttpClient) HttpClient.create()
        /*  81 */.doOnConnected(setConnection(readTimeout, writeTimeout)))
        /*  82 */.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connectionTimeout.toMillis()))
        /*  83 */.wiretap(true);
    /*     */
  }

  /*     */
  /*     */
  protected Function<ClientResponse, Mono<? extends Throwable>> log() {
    /*  87 */
    return (clientResponse) -> {
      return clientResponse.createException()
          .doOnNext((ex) -> {
            this.getLogger()
                .error("Web client response exception: {}", ex.getResponseBodyAsString());
          });
    };
    /*     */
  }

  /*     */
  /*     */
  /*     */
  protected void setClient() {
    /*  92 */
    this
        /*     */
        /*  94 */.client = WebClient.builder()
        .apply(createClient())
        .build();
    /*     */
  }

  /*     */
  /*     */
  protected Consumer<Connection> setConnection(Duration readTimeout, Duration writeTimeout) {
    /*  98 */
    return conn -> conn.addHandlerLast((ChannelHandler) new ReadTimeoutHandler((int) readTimeout.toMillis()))
        .addHandlerLast((ChannelHandler) new WriteTimeoutHandler((int) writeTimeout.toMillis()));
    /*     */
  }

  /*     */
  /*     */
  /*     */
  private void setClientConfig() {
    /* 103 */
    this
        /* 104 */.clientConfig = (WebClientProperties.ClientConfig) this.clientProperties.getConfig()
        .get(this.configName);
    /*     */
  }

  /*     */
  /*     */
  protected abstract Logger getLogger();
  /*     */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web-client/0.5.13/abinarystar-spring-web-client-0.5.13.jar!/com/abinarystar/spring/web/client/reactive/BaseWebClient.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */