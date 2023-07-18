package com.solusinegeri.common.service;

import java.time.Instant;
import java.util.Date;

public interface TimeService {

  Date getCurrentTime();

  Instant getCurrentTimeInstant();

  long getCurrentTimeMillis();
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/service/TimeService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */