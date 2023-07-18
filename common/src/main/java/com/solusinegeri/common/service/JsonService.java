package com.solusinegeri.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.lang.NonNull;

public interface JsonService {

  String map(@NonNull Object paramObject);

  <T> T map(@NonNull String paramString, @NonNull Class<T> paramClass);

  <T> T map(@NonNull String paramString, @NonNull TypeReference<T> paramTypeReference);
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-common/0.5.13/abinarystar-spring-common-0.5.13.jar!/com/abinarystar/spring/common/service/JsonService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */