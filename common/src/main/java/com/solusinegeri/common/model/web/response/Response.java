package com.solusinegeri.common.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {

  private T data;

  private List<String> errorCodes;

  private Map<String, List<String>> errors;

  private Paging paging;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Paging {

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
  }
}
