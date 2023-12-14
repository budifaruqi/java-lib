/*    */
package com.solusinegeri.test.web.servlet;
/*    */
/*    */

import com.solusinegeri.security.service.servlet.impl.AuthorizationCookieTokenService;
import com.solusinegeri.security.service.servlet.impl.AuthorizationHeaderTokenService;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.servlet.SessionService;
import com.solusinegeri.session.service.servlet.UserService;
import com.solusinegeri.test.TestHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Optional;

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
/*    */
/*    */ abstract class BaseControllerTestHelper
    /*    */ extends TestHelper
    /*    */ {

  /*    */   protected static final String AUTH_TOKEN = "auth-token";

  /*    */
  @MockBean
  /*    */ protected AuthorizationCookieTokenService cookieTokenService;

  /*    */
  @MockBean
  /*    */ protected AuthorizationHeaderTokenService headerTokenService;

  /*    */
  @Autowired
  /*    */ protected TestRestTemplate restTemplate;

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
    BDDMockito.given(this.cookieTokenService.get((HttpServletRequest) ArgumentMatchers.any(HttpServletRequest.class)))
        .willReturn(Optional.of("auth-token"));
    /* 47 */
    BDDMockito.given(this.headerTokenService.get((HttpServletRequest) ArgumentMatchers.any(HttpServletRequest.class)))
        .willReturn(Optional.of("auth-token"));
    /* 48 */
    BDDMockito.given(this.sessionService.find("auth-token"))
        .willReturn(Optional.of(this.session));
    /* 49 */
    ((SessionService) BDDMockito.willDoNothing()
        .given(this.sessionService))
        /* 50 */.modifySession((HttpServletRequest) ArgumentMatchers.any(HttpServletRequest.class),
        (Session) ArgumentMatchers.eq(this.session));
    /* 51 */
    BDDMockito.given(Boolean.valueOf(this.userService.hasPermission((Session) ArgumentMatchers.eq(this.session),
            (String) ArgumentMatchers.any(String.class))))
        .willReturn(Boolean.TRUE);
    /* 52 */
    BDDMockito.given(Boolean.valueOf(
            this.userService.hasRole((Session) ArgumentMatchers.eq(this.session), ArgumentMatchers.anyList())))
        .willReturn(Boolean.TRUE);
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-test/0.5.13/abinarystar-spring-test-0.5.13.jar!/com/abinarystar/spring/test/web/servlet/BaseControllerTestHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */