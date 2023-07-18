package com.solusinegeri.common.service;

import org.springframework.lang.NonNull;

import java.io.IOException;

public interface ResourceService {

  String readAsString(@NonNull String paramString) throws IOException;
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/service/ResourceService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */