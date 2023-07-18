/*    */
package com.solusinegeri.swagger.autoconfigure;
/*    */
/*    */

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.Optional;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
@Configuration
/*    */
@PropertySource({"classpath:swagger.properties"})
/*    */ public class SwaggerAutoConfiguration
    /*    */ {

  /*    */   public static final String DEFAULT_APPLICATION_NAME = "Application";

  /*    */   public static final String DEFAULT_APPLICATION_VERSION = "SNAPSHOT";

  /*    */   private static final String SECURITY_SCHEME_BEARER = "bearer";

  /*    */   private static final String SECURITY_SCHEME_NAME = "auth";

  /*    */
  @Value("${spring.application.name:}")
  /*    */ private String applicationName;

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public OpenAPI openAPI(@Autowired(required = false) BuildProperties buildProperties) {
    /* 38 */
    return (new OpenAPI())
        /*    */
        /* 40 */.addSecurityItem((new SecurityRequirement())
            /*    */
            /* 42 */.addList("auth", Collections.emptyList()))
        /*    */
        /* 44 */.components((new Components())
            /*    */
            /* 46 */.addSecuritySchemes("auth", (new SecurityScheme())
                /*    */
                /* 48 */.scheme("bearer")
                /* 49 */.type(SecurityScheme.Type.HTTP)))
        /*    */
        /* 51 */.info((new Info())
            /*    */
            /* 53 */.title(getApplicationName(buildProperties))
            /* 54 */.version(getApplicationVersion(buildProperties)));
    /*    */
  }

  /*    */
  /*    */
  private String getApplicationName(BuildProperties buildProperties) {
    /* 58 */
    return Optional.<String>ofNullable(this.applicationName)
        /* 59 */.filter(StringUtils::isNotBlank)
        /* 60 */.orElseGet(() -> (String) getApplicationNameFromBuildProperties(buildProperties).orElse("Application"));
    /*    */
  }

  /*    */
  /*    */
  private Optional<String> getApplicationNameFromBuildProperties(BuildProperties buildProperties) {
    /* 64 */
    return Optional.<BuildProperties>ofNullable(buildProperties)
        /* 65 */.map(BuildProperties::getName);
    /*    */
  }

  /*    */
  /*    */
  private String getApplicationVersion(BuildProperties buildProperties) {
    /* 69 */
    return Optional.<BuildProperties>ofNullable(buildProperties)
        /* 70 */.map(BuildProperties::getVersion)
        /* 71 */.orElse("SNAPSHOT");
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-swagger/0.5.13/abinarystar-spring-swagger-0.5.13.jar!/com/abinarystar/spring/swagger/autoconfigure/SwaggerAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */