package counter.app;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * pradeep.thakur Oct 12, 2022
 *
 *
 *
 *
 */
@WebFilter("/header*")
@Component
@Slf4j
public class AuthTokenValidatorFilter implements Filter {

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse servletResponse,
      final FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    Enumeration<String> headerNames = httpRequest.getHeaderNames();

    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        // System.out.println("Header: " + httpRequest.getHeader(headerNames.nextElement()));
        log.info("header : {}", httpRequest.getHeader(headerNames.nextElement()));
      }
    }

    // doFilter
    chain.doFilter(httpRequest, servletResponse);

  }

  @Override
  public void destroy() {}
}

