package com.ciandt.techgallery.security;

import com.google.api.client.http.HttpStatusCodes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for responses on authorization exceptions due to domain issues.
 *
 * @author <a href="mailto:mpellini@ciandt.com"> Marcos Fernandes </a>
 * @since 09/01/2017
 *
 */
public class RestrictedDomainFilter implements Filter {

  private static final Logger log = Logger.getLogger(RestrictedDomainFilter.class.getName());
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // no need to do anything.
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // Throws 401 when RestrictedDomainException is handled..  
    try {
      chain.doFilter(request, response);
    } catch (RestrictedDomainException e) {
      log.log(Level.WARNING, e.getMessage(), e);
      response.getWriter().print(e.getMessage());
      ((HttpServletResponse) response).setStatus(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
    }
  }

  @Override
  public void destroy() {
    // no need to do anything.
  }
}
