/*    */
package com.solusinegeri.data.helper;

import com.solusinegeri.data.model.BaseEntity;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class EntityHelper
    /*    */ {

  /*    */
  public static <T extends BaseEntity> void markForDeleted(T entity) {
    /* 12 */
    entity.setDeleted(true);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-data/0.5.13/abinarystar-spring-data-0.5.13.jar!/com/abinarystar/spring/data/helper/EntityHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */