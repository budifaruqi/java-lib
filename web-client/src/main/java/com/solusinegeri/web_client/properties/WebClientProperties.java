package com.solusinegeri.web_client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties("abinarystar.web.client")
public class WebClientProperties {

  private Map<String, ClientConfig> config = new HashMap<>();

  @Data
  public static class ClientConfig {

    private String basePath;

    private Duration connectionTimeout = Duration.ofSeconds(2L);

    private String hostName;

    private Duration readTimeout = Duration.ofSeconds(2L);

    private Duration writeTimeout = Duration.ofSeconds(2L);
  }
}
