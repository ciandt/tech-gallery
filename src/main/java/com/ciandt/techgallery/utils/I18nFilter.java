package com.ciandt.techgallery.utils;

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
	  // @TODO: Check for the "lang" parameter so it's possible to "force" a specific language.
	  
      I18n i18n = I18n.getInstance();
	  Locale accept_language = request.getLocale();
	  i18n.setLanguage(accept_language.getLanguage());
	  i18n.setCountry(accept_language.getCountry());
      
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
