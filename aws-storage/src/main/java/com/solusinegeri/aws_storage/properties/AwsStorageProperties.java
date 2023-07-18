/*    */
package com.solusinegeri.aws_storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("abinarystar.aws.storage")
public class AwsStorageProperties {

  private String accessKey;

  private String bucketName;

  private String secretKey;

  private String serviceEndpoint;

  private String signingRegion;
}
