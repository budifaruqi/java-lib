/*    */
package com.solusinegeri.test.web.client;
/*    */
/*    */

import com.solusinegeri.common.service.JsonService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public abstract class WebClientTestHelper
    /*    */ {

  /*    */   protected static final String EMPTY_JSON = "{}";

  /*    */   protected static MockWebServer server;

  /*    */
  @Autowired
  /*    */ protected JsonService jsonService;

  /*    */
  /*    */
  @AfterAll
  /*    */ static void afterAll() throws Exception {
    /* 27 */
    server.shutdown();
    /*    */
  }

  /*    */
  /*    */
  @BeforeAll
  /*    */ static void beforeAll() throws Exception {
    /* 32 */
    server = new MockWebServer();
    /* 33 */
    server.start(10000);
    /*    */
  }

  /*    */
  /*    */
  protected void prepareErrorResponse() {
    /* 37 */
    prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    /*    */
  }

  /*    */
  /*    */
  protected void prepareErrorResponse(HttpStatus httpStatus) {
    /* 41 */
    prepareResponse(response -> response.setResponseCode(httpStatus.value()));
    /*    */
  }

  /*    */
  /*    */
  protected void prepareResponse(Consumer<MockResponse> consumer) {
    /* 45 */
    MockResponse response = new MockResponse();
    /* 46 */
    consumer.accept(response);
    /* 47 */
    server.enqueue(response);
    /*    */
  }

  /*    */
  /*    */
  protected void prepareSuccessResponse(Object clientResponse) {
    /* 51 */
    prepareResponse(response -> response.setResponseCode(HttpStatus.OK.value())
        .addHeader("Content-Type", "application/json")
        .setBody(this.jsonService.map(clientResponse)));
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  protected void prepareSuccessResponse() {
    /* 57 */
    prepareSuccessResponse("{}");
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/web/client/WebClientTestHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */