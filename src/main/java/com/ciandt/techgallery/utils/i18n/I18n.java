package com.ciandt.techgallery.utils.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {

  private static I18n instance = new I18n();

  private String language = "en";
  private String country = "US";

  public I18n() {}

  /**
   * Get the message in the file for the respective language.
   * @param string message.
   * @return translated message.
   */
  public String t(String string) {
    Locale locale = new Locale(language, country);
    ResourceBundle.clearCache();
    ResourceBundle translation = ResourceBundle.getBundle("i18n/Tech_Gallery", locale);
    return translation.getString(string);
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public static I18n getInstance() {
    return instance;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
