/*    */
package com.solusinegeri.common.helper;
/*    */
/*    */

import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class ResourceHelper
    /*    */ {

  /*    */
  public static String readAsString(@NonNull String path) throws IOException {
    /*    */
    try {
      /* 17 */
      File file = (new ClassPathResource(path)).getFile();
      /* 18 */
      return Files.readString(file.toPath());
      /*    */
    } catch (Throwable $ex) {
      /*    */
      throw $ex;
      /*    */
    }
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/helper/ResourceHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */