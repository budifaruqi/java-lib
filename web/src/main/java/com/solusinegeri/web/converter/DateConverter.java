/*    */
package com.solusinegeri.web.converter;
/*    */
/*    */

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*    */
/*    */
/*    */ public class DateConverter
    /*    */ implements Converter<String, Date>
    /*    */ {

  /*    */
  public Date convert(@NonNull String source) {
    /* 14 */
    ZonedDateTime dateTime = ZonedDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
    /* 15 */
    return Date.from(dateTime.toInstant());
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/converter/DateConverter.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */