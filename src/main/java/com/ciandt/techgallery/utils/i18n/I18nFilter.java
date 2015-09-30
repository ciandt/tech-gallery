package com.ciandt.techgallery.utils.i18n;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class I18nFilter implements Filter {

  private FilterConfig filterConfig;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    I18n i18n = I18n.getInstance();

    // Checks if appengine is the one making the request. In this case, do nothing.
    String appEngineHeader = ((HttpServletRequest) request).getHeader("X-Appengine-Peer");
    if (appEngineHeader == null) {
      if (request.getParameter("lang") == null) {
        Locale acceptLanguage = request.getLocale();
        i18n.setLanguage(acceptLanguage.getLanguage());
        i18n.setCountry(acceptLanguage.getCountry());
      } else {
        String[] lang = request.getParameter("lang").split("-");
        String language = lang[0];
        String country = lang[1];
        i18n.setCountry(country);
        i18n.setLanguage(language);
      }
    }

    filterChain.doFilter(request, response);
  }

  public FilterConfig getFilterConfig() {
    return filterConfig;
  }

  @Override
  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public void destroy() {}

}
