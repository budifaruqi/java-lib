/*    */
package com.solusinegeri.aws_storage.service;
/*    */
/*    */

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.lang.Nullable;

import java.io.File;

/*    */
/*    */
/*    */ public interface AwsStorageService
    /*    */ {

  /*    */   void deleteFile(String paramString);

  /*    */
  /*    */
  @Nullable
  /*    */   byte[] getFile(String paramString);

  /*    */
  /*    */   PutObjectResult putFile(String paramString, File paramFile,
      CannedAccessControlList paramCannedAccessControlList);

  /*    */
  /*    */
  default PutObjectResult putPrivateFile(String key, File file) {
    /* 19 */
    return putFile(key, file, CannedAccessControlList.Private);
    /*    */
  }

  /*    */
  /*    */
  default PutObjectResult putPublicFile(String key, File file) {
    /* 23 */
    return putFile(key, file, CannedAccessControlList.PublicRead);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-aws-storage/0.5.13/abinarystar-spring-aws-storage-0.5.13.jar!/com/abinarystar/spring/aws/storage/service/AwsStorageService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */