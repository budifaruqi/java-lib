package com.solusinegeri.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties("abinarystar.security")
public class SecurityProperties {

  private String cookieDomain;

  private Duration cookieMaxAge = Duration.ofSeconds(-1L);

  private String cookiePath = "/";

  private String cookieSameSite = "Lax";

  private String cookieSessionKey = "sessionId";

  private String tokenSource;
}
