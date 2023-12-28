package com.example.test.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailClientResponse<T> {

  @JsonProperty("status_code")
  private String status_code;

  private String type;

  private String message;

  private T data;
}
