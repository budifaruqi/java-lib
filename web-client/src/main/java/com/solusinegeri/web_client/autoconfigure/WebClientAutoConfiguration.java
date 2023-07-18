package com.solusinegeri.web_client.autoconfigure;

import com.solusinegeri.web_client.advice.WebClientExceptionControllerAdvice;
import com.solusinegeri.web_client.properties.WebClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({WebClientProperties.class})
@Import({WebClientExceptionControllerAdvice.class})
public class WebClientAutoConfiguration {}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web-client/0.5.13/abinarystar-spring-web-client-0.5.13.jar!/com/abinarystar/spring/web/client/autoconfigure/WebClientAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */