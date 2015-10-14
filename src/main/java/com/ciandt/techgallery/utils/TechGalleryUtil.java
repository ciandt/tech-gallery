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
    String appVersion = SystemProperty.applicationVersion.get();
    if (!appVersion.contains("$")) {
      String namespace = appVersion;
      String[] version = appVersion.split("\\.");
      if (version.length > 0 && version[0].contains("-")) {
        namespace = version[0].split("-")[0];
      } else if (version.length > 0) {
        namespace = version[0];
      } else {
        namespace = appVersion;
      }
      return namespace;
    }
    return appVersion;
  }
}
