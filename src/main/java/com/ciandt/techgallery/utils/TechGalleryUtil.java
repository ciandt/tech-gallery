package com.ciandt.techgallery.utils;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Class with the project's util.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 13/10/2015
 *
 */
public class TechGalleryUtil {

  /*
   * Methods --------------------------------------------
   */
  /**
   * Method to get the application version.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @return the application version.
   */
  public static String getApplicationVersion() {
    return SystemProperty.applicationVersion.get();
  }
}
