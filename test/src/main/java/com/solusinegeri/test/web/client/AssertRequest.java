/*    */
package com.solusinegeri.test.web.client;
/*    */
/*    */

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.List;

/*    */
/*    */
/*    */
/*    */ public class AssertRequest
    /*    */ {

  /*    */   private final RecordedRequest recordedRequest;

  /*    */
  /*    */
  private AssertRequest(RecordedRequest recordedRequest) {
    /* 19 */
    this.recordedRequest = recordedRequest;
    /*    */
  }

  /*    */
  /*    */
  public static AssertRequest from(MockWebServer server) throws InterruptedException {
    /* 23 */
    return new AssertRequest(server.takeRequest());
    /*    */
  }

  /*    */
  /*    */
  public void body(String body) {
    /* 27 */
    Buffer buffer = this.recordedRequest.getBody();
    /* 28 */
    Assertions.assertThat(buffer.readUtf8())
        .isEqualTo(body);
    /*    */
  }

  /*    */
  /*    */
  public AssertRequest header(String name, String value) {
    /* 32 */
    Assertions.assertThat(this.recordedRequest.getHeader(name))
        .isEqualTo(value);
    /* 33 */
    return this;
    /*    */
  }

  /*    */
  /*    */
  public AssertRequest method(HttpMethod httpMethod) {
    /* 37 */
    Assertions.assertThat(this.recordedRequest.getMethod())
        .isEqualTo(httpMethod.name());
    /* 38 */
    return this;
    /*    */
  }

  /*    */
  /*    */
  public AssertRequest param(String name, String... values) {
    /* 42 */
    HttpUrl httpUrl = this.recordedRequest.getRequestUrl();
    /* 43 */
    Assertions.assertThat(httpUrl)
        .isNotNull();
    /* 44 */
    List<String> parameterValues = httpUrl.queryParameterValues(name);
    /* 45 */
    Assertions.assertThat(parameterValues)
        .containsAnyOf(values);
    /* 46 */
    return this;
    /*    */
  }

  /*    */
  /*    */
  public AssertRequest path(String path) {
    /* 50 */
    HttpUrl httpUrl = this.recordedRequest.getRequestUrl();
    /* 51 */
    Assertions.assertThat(httpUrl)
        .isNotNull();
    /* 52 */
    URI uri = httpUrl.uri();
    /* 53 */
    Assertions.assertThat(uri.getPath())
        .isEqualTo(path);
    /* 54 */
    return this;
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/web/client/AssertRequest.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */