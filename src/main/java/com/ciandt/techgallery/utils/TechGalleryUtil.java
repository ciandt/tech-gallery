package com.ciandt.techgallery.utils;

import com.google.appengine.api.utils.SystemProperty;
import com.google.apphosting.api.ApiProxy;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Class with the project's util.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 13/10/2015
 *
 */
public class TechGalleryUtil {

  private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

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

  /**
   * Method to slugify a name.
   *
   * @param name name to be changed.
   * @return Changed name.
   */
  public static String slugify(String name) {
    String nowhitespace = WHITESPACE.matcher(name).replaceAll("_");
    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH);
  }

  /**
   * Method to get the app id and if necessary, remove a '~' of the name.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 16/10/2015
   *
   * @return the app id formated.
   */
  public static String getAppId() {
    String appId = ApiProxy.getCurrentEnvironment().getAppId();
    int tilde = appId.indexOf('~');
    if (tilde >= 0) {
      appId = appId.substring(tilde + 1);
    }
    return appId;
  }
}
