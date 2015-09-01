package com.ciandt.techgallery.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton instance for the i18n mechanism.
 * 
 * @author lucas
 *
 */
public class I18n {
	
	private String language = "en";
	private String country = "US";
	
    static I18n instance = new I18n();
	
	private I18n() {
		
	}
	
	public static I18n getInstance() {
		return instance;
	}
	
	public String t(String string) {
		Locale currentLocale = new Locale(language, country);
		ResourceBundle translation = ResourceBundle.getBundle("resources/i18n/TechGallery", currentLocale);
		return translation.getString(string);
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {	
		this.language = language;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
}
