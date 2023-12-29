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
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
  private String applicationName;

  @Bean
  @ConditionalOnClass(SwaggerAutoConfiguration.class)
  public OpenAPI openAPI(@Autowired(required = false) BuildProperties buildProperties) {
    SecurityScheme apiKeyScheme = new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .in(SecurityScheme.In.HEADER)
        .name("X-ASIS-COMPANYID")
        .description("API Key for X-ASIS-COMPANYID");

    return (new OpenAPI())
        .addSecurityItem((new SecurityRequirement())
            .addList("auth", Collections.emptyList()))
        .addSecurityItem(new SecurityRequirement().addList("X-ASIS-COMPANYID", Collections.emptyList()))

        .components((new Components())
            .addSecuritySchemes("auth", (new SecurityScheme()).scheme("bearer")
                .type(SecurityScheme.Type.HTTP))
            .addSecuritySchemes("X-ASIS-COMPANYID", apiKeyScheme))
        .info((new Info()).title(this.getApplicationName(buildProperties))
            .version(this.getApplicationVersion(buildProperties)));
  }

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