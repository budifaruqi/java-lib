/*    */
package com.solusinegeri.common.service.impl;
/*    */
/*    */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solusinegeri.common.service.JsonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*    */
/*    */
/*    */
@Service
public class JsonServiceImpl implements JsonService {

  private final ObjectMapper objectMapper;

  @Autowired
  public JsonServiceImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String map(@NonNull Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Throwable ex) {
      throw new RuntimeException("Error while mapping object to JSON: " + ex.getMessage(), ex);
    }
  }

  @Override
  public <T> T map(@NonNull String json, @NonNull Class<T> objectClass) {
    try {
      return objectMapper.readValue(json, objectClass);
    } catch (Throwable ex) {
      throw new RuntimeException("Error while mapping JSON to object: " + ex.getMessage(), ex);
    }
  }

  @Override
  public <T> T map(@NonNull String json, @NonNull TypeReference<T> objectTypeReference) {
    try {
      return objectMapper.readValue(json, objectTypeReference);
    } catch (Throwable ex) {
      throw new RuntimeException("Error while mapping JSON to object: " + ex.getMessage(), ex);
    }
  }
}