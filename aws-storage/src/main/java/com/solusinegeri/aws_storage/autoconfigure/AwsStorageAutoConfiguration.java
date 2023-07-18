/*    */
package com.solusinegeri.aws_storage.autoconfigure;
/*    */
/*    */

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.solusinegeri.aws_storage.properties.AwsStorageProperties;
import com.solusinegeri.aws_storage.service.AwsStorageService;
import com.solusinegeri.aws_storage.service.impl.AwsStorageServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*    */
/*    */
/*    */
@Configuration
/*    */
@EnableConfigurationProperties({AwsStorageProperties.class})
/*    */ public class AwsStorageAutoConfiguration
    /*    */ {

  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public AmazonS3 amazonS3(AWSCredentialsProvider awsCredentialsProvider,
      AwsStorageProperties awsStorageProperties) {
    /* 26 */
    AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
        awsStorageProperties.getServiceEndpoint(), awsStorageProperties.getSigningRegion());
    /* 27 */
    return (AmazonS3) ((AmazonS3ClientBuilder) ((AmazonS3ClientBuilder) AmazonS3ClientBuilder.standard()
        /* 28 */.withCredentials(awsCredentialsProvider))
        /* 29 */.withEndpointConfiguration(endpointConfiguration))
        /* 30 */.build();
    /*    */
  }

  /*    */
  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public AWSCredentialsProvider awsCredentialsProvider(AwsStorageProperties awsStorageProperties) {
    /* 37 */
    BasicAWSCredentials credentials = new BasicAWSCredentials(awsStorageProperties.getAccessKey(),
        awsStorageProperties.getSecretKey());
    /* 38 */
    return (AWSCredentialsProvider) new AWSStaticCredentialsProvider((AWSCredentials) credentials);
    /*    */
  }

  /*    */
  /*    */
  @Bean
  /*    */
  @ConditionalOnMissingBean
  /*    */ public AwsStorageService awsStorageService(AmazonS3 amazonS3, AwsStorageProperties awsStorageProperties) {
    /* 44 */
    return (AwsStorageService) new AwsStorageServiceImpl(amazonS3, awsStorageProperties);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-aws-storage/0.5.13/abinarystar-spring-aws-storage-0.5.13.jar!/com/abinarystar/spring/aws/storage/autoconfigure/AwsStorageAutoConfiguration.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */