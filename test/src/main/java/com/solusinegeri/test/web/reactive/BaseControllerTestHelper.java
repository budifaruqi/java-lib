/*    */
package com.solusinegeri.test.web.reactive;
/*    */
/*    */

import com.solusinegeri.security.service.reactive.impl.AuthorizationCookieTokenService;
import com.solusinegeri.security.service.reactive.impl.AuthorizationHeaderTokenService;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.reactive.SessionService;
import com.solusinegeri.session.service.reactive.UserService;
import com.solusinegeri.test.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
@AutoConfigureWebTestClient
/*    */ abstract class BaseControllerTestHelper
    /*    */ extends TestHelper
    /*    */ {

  /*    */   protected static final String AUTH_TOKEN = "auth-token";

  /*    */
  @Autowired
  /*    */ protected WebTestClient client;

  /*    */
  @MockBean
  /*    */ protected AuthorizationCookieTokenService cookieTokenService;

  /*    */
  @MockBean
  /*    */ protected AuthorizationHeaderTokenService headerTokenService;

  /*    */
  @MockBean
  /*    */ protected SessionService sessionService;

  /*    */
  @MockBean
  /*    */ protected UserService userService;

  /*    */
  /*    */
  @BeforeEach
  /*    */ protected void setUp() {
    /* 45 */
    super.setUp();
    /* 46 */
    BDDMockito.given(this.cookieTokenService.get((ServerHttpRequest) ArgumentMatchers.any(ServerHttpRequest.class)))
        .willReturn(Mono.just("auth-token"));
    /* 47 */
    BDDMockito.given(this.headerTokenService.get((ServerHttpRequest) ArgumentMatchers.any(ServerHttpRequest.class)))
        .willReturn(Mono.just("auth-token"));
    /* 48 */
    BDDMockito.given(this.sessionService.find("auth-token"))
        .willReturn(Mono.just(this.session));
    /* 49 */
    BDDMockito.given(
            this.sessionService.modifySession((ServerHttpRequest) ArgumentMatchers.any(ServerHttpRequest.class),
                (Session) ArgumentMatchers.eq(this.session)))
        .willReturn(Mono.empty());
    /* 50 */
    BDDMockito.given(this.userService.hasPermission((Session) ArgumentMatchers.eq(this.session),
            (String) ArgumentMatchers.any(String.class)))
        .willReturn(Mono.just(Boolean.TRUE));
    /* 51 */
    BDDMockito.given(this.userService.hasRole((Session) ArgumentMatchers.eq(this.session), ArgumentMatchers.anyList()))
        .willReturn(Mono.just(Boolean.TRUE));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/web/reactive/BaseControllerTestHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */