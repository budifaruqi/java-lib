/*    */
package com.solusinegeri.common.service.impl;
/*    */

import com.solusinegeri.common.helper.ResourceHelper;
import com.solusinegeri.common.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.IOException;

/*    */
/*    */ public class ResourceServiceImpl implements ResourceService {

  /*  9 */   private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

  /*    */
  /*    */
  /*    */
  /*    */
  public String readAsString(@NonNull String path) throws IOException {
    /* 14 */
    return ResourceHelper.readAsString(path);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/service/impl/ResourceServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */