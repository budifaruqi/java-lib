/*    */
package com.solusinegeri.security.filter;
/*    */
/*    */

import com.solusinegeri.security.helper.SecurityHelper;
import com.solusinegeri.security.service.servlet.TokenService;
import com.solusinegeri.session.model.Session;
import com.solusinegeri.session.service.servlet.SessionService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*    */
/*    */ public class ServletSecurityFilter
    /*    */ implements Filter
    /*    */ {

  /*    */   private final SessionService sessionService;

  /*    */   private final List<TokenService> tokenServices;

  /*    */
  /*    */
  public ServletSecurityFilter(SessionService sessionService, List<TokenService> tokenServices) {
    /* 27 */
    this.sessionService = sessionService;
    /* 28 */
    this.tokenServices = tokenServices;
    /*    */
  }

  /*    */
  /*    */
  /*    */
  /*    */
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    /* 34 */
    Session session = findSession((HttpServletRequest) servletRequest);
    /* 35 */
    setSecurityContext(session);
    /* 36 */
    filterChain.doFilter(servletRequest, servletResponse);
    /*    */
  }

  /*    */
  /*    */
  /*    */
  private Session findSession(HttpServletRequest request) {
    /* 41 */
    Objects.requireNonNull(this.sessionService);
    return getToken(request).flatMap(this.sessionService::find)
        /* 42 */.map(session -> modifySession(request, session))
        /* 43 */.orElse(SecurityHelper.ANONYMOUS_SESSION);
    /*    */
  }

  /*    */
  /*    */
  private Optional<String> getToken(HttpServletRequest request) {
    /* 47 */
    return this.tokenServices.stream()
        /* 48 */.map(tokenService -> tokenService.get(request))
        /* 49 */.filter(Optional::isPresent)
        /* 50 */.map(Optional::get)
        /* 51 */.findFirst();
    /*    */
  }

  /*    */
  /*    */
  private Session modifySession(HttpServletRequest request, Session session) {
    /* 55 */
    this.sessionService.modifySession(request, session);
    /* 56 */
    return session;
    /*    */
  }

  /*    */
  /*    */
  private void setSecurityContext(Session session) {
    /* 60 */
    SecurityContextHolder.getContext()
        /* 61 */.setAuthentication((Authentication) SecurityHelper.createAuthentication(session));
    /*    */
  }
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-security/0.5.13/abinarystar-spring-security-0.5.13.jar!/com/abinarystar/spring/security/filter/ServletSecurityFilter.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */