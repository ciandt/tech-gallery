package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.dao.ApplicationConfigurationDAO;
import com.ciandt.techgallery.persistence.dao.impl.ApplicationConfigurationDAOImpl;
import com.ciandt.techgallery.persistence.model.ApplicationConfiguration;
import com.ciandt.techgallery.security.RestrictedDomainException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Authenticator;
import com.google.api.server.spi.config.ApiReference;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.ciandt.techgallery.utils.i18n.I18n;


import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Arrays;

public class TechGalleryAuthenticator implements Authenticator {

  private static final I18n i18n = I18n.getInstance(); 
  private static final Logger log = Logger.getLogger(TechGalleryAuthenticator.class.getName()); 
  private static final ApplicationConfigurationDAO appConfigDAO = ApplicationConfigurationDAOImpl.getInstance();

  @Override
  public User authenticate(HttpServletRequest req) {
      OAuthService authService = OAuthServiceFactory.getOAuthService();
      com.google.appengine.api.users.User currentUser;

      try {
        currentUser = authService.getCurrentUser(Constants.EMAIL_SCOPE);
        // Check current user..
        if(currentUser != null) {
          String email = currentUser.getEmail();
          // Check domain..
          if(isValidDomain(email) || isWhiteList(email)) {
            return new User(currentUser.getUserId(), currentUser.getEmail());
          }
        }
        throw new RestrictedDomainException(i18n.t("Authorization error"));
      }
      catch(OAuthRequestException  e) {
        log.log(Level.WARNING, "Error when trying to authenticate. Message: " + e.getMessage(), e);
        return null;
      }
  }

  /**
   * Check if a domain is valid.
   */
  private boolean isValidDomain(String email) {
    ApplicationConfiguration domain = appConfigDAO.findOrCreateById("allowed-domains");
    if(domain != null) {
      email = email.toLowerCase();
      // Check empty records..
      if(domain.getValue() == null || domain.getValue() == "") {
        return false;
      }
      // Check all allowed domains on settings..
      String[] allowedDomains = domain.getValue().toLowerCase().split(",");
      for(String ad : allowedDomains) {
        if(email.endsWith(ad)) {
          log.info("User (" + email + ") has matched domain " + ad);
          return true;
        }
      }
      // In case there is no match with a domain, invalidate user..
      return false;
    }
    // In case there is no setting, insert setting to create kind and authorize all..
    return true;
  }

  /**
   * Check if user is part of whitelist.
   */
  private boolean isWhiteList(String email) {
    ApplicationConfiguration whiteList = appConfigDAO.findOrCreateById("email-white-list");
    if(whiteList != null) {
      email = email.toLowerCase();
      // There is no value, which means no whitelist..
      if(whiteList.getValue() == null || whiteList.getValue() == "") {
        return false;
      }
      // Check all allowed e-mail addresses on settings..
      String[] allowedAddresses = whiteList.getValue().toLowerCase().split(",");
      return Arrays.asList(allowedAddresses).contains(email);
    }
    // In case there is no setting, invalidate as there is no white list.
    return false;
  }

}