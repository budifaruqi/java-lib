package com.solusinegeri.session.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("abinarystar.session")
public class SessionProperties {

  private int passwordEncoderStrength = -1;
}
