/*    */
package com.solusinegeri.aws_storage.service.impl;
/*    */
/*    */

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.solusinegeri.aws_storage.properties.AwsStorageProperties;
import com.solusinegeri.aws_storage.service.AwsStorageService;

import java.io.File;
import java.io.InputStream;

/*    */
/*    */
/*    */ public class AwsStorageServiceImpl
    /*    */ implements AwsStorageService
    /*    */ {

  /*    */   private final AmazonS3 amazonS3;

  /*    */   private final AwsStorageProperties awsStorageProperties;

  /*    */
  /*    */
  public AwsStorageServiceImpl(AmazonS3 amazonS3, AwsStorageProperties awsStorageProperties) {
    /* 22 */
    this.amazonS3 = amazonS3;
    /* 23 */
    this.awsStorageProperties = awsStorageProperties;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public void deleteFile(String key) {
    /* 28 */
    this.amazonS3.deleteObject(this.awsStorageProperties.getBucketName(), key);
    /*    */
  }

  /*    */
  /*    */
  public byte[] getFile(String key) {
    /*    */
    /* 33 */
    try {
      S3Object object = this.amazonS3.getObject(this.awsStorageProperties.getBucketName(), key);
      /* 34 */
      try {
        byte[] arrayOfByte = IOUtils.toByteArray((InputStream) object.getObjectContent());
        /* 35 */
        if (object != null) {
          object.close();
        }
        return arrayOfByte;
      } catch (Throwable throwable) {
        if (object != null) {
          try {
            object.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }
        }
        throw throwable;
      }
    } catch (Exception ex)
      /* 36 */ {
      throw new RuntimeException("AWS exception", ex);
    }
    /*    */
    /*    */
  }

  /*    */
  /*    */
  /*    */
  public PutObjectResult putFile(String key, File file, CannedAccessControlList acl) {
    /* 42 */
    PutObjectRequest request = new PutObjectRequest(this.awsStorageProperties.getBucketName(), key, file);
    /* 43 */
    PutObjectResult result = this.amazonS3.putObject(request.withCannedAcl(acl));
    /* 44 */
    file.delete();
    /* 45 */
    return result;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-aws-storage/0.5.13/abinarystar-spring-aws-storage-0.5.13.jar!/com/abinarystar/spring/aws/storage/service/impl/AwsStorageServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */