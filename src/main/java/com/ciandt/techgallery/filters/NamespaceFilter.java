package com.ciandt.techgallery.filters;

import com.google.appengine.api.NamespaceManager;

import com.ciandt.techgallery.utils.TechGalleryUtil;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter for the database namespace.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 01/10/2015
 *
 */
public class NamespaceFilter implements Filter {

  /*
   * Constants --------------------------------------------
   */
  private static final Logger logger = Logger.getLogger(NamespaceFilter.class.getName());

  /*
   * Attributes --------------------------------------------
   */
  private FilterConfig filterConfig;

  /*
   * Methods --------------------------------------------
   */
  @Override
  public void init(FilterConfig config) throws ServletException {
    filterConfig = config;
  }

  @Override
  public void destroy() {}

  /**
   * {@inheritDoc}. Each request this method set the namespace. (Namespace is by version)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    // If the NamespaceManager state is already set up from the context of the task creator the
    // current namespace will not be null. It's important to check that the current namespace
    // has not been set before setting it for this request.
    String applicationVersion = TechGalleryUtil.getApplicationVersion();

    if (NamespaceManager.get() == null && !applicationVersion.contains("$")) {
      logger.info("SystemProperty.applicationVersion.get():" + applicationVersion);
      String[] version = applicationVersion.split("\\.");
      if (version.length > 0 && version[0].contains("-")) {
        String namespace = version[0].split("-")[0];
        NamespaceManager.set(namespace);
      } else if (version.length > 0) {
        NamespaceManager.set(version[0]);
      } else {
        NamespaceManager.set(applicationVersion);
      }

      logger.info("set namespace:" + NamespaceManager.get());
    }
    // chain into the next request
    chain.doFilter(request, response);
  }

  public FilterConfig getFilterConfig() {
    return filterConfig;
  }
}
