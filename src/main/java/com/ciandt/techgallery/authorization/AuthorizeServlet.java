package com.ciandt.techgallery.authorization;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

public class AuthorizeServlet extends AbstractAppEngineAuthorizationCodeServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger log = Logger.getLogger(TechGalleryUser.class.getName());

  /**
   * Gets the current user and their credentials
   */
  @Override
  public void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
      throws ServletException, IOException {
    AuthorizationCodeFlow authFlow = initializeFlow();
    Credential credential = authFlow.loadCredential(getUserId(req));

    Plus plus =
        new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
            .setApplicationName("Tech Gallery").build();
    Person mePerson = plus.people().get("me").execute();

    TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();

    TechGalleryUser techGalleryUser = techGalleryUserDAO.findByGplusId(mePerson.getId());
    if (techGalleryUser == null) {
      techGalleryUser = new TechGalleryUser();
      techGalleryUser.setGplusId(mePerson.getId());
      techGalleryUser.setName(mePerson.getDisplayName());
      techGalleryUser.setPhoto(mePerson.getImage().getUrl());
      if (mePerson.getEmails() != null)
        techGalleryUser.setEmail(mePerson.getEmails().get(0).getValue());
      techGalleryUserDAO.add(techGalleryUser);
      log.info("User created: " + mePerson.getDisplayName());
    } else {
      techGalleryUser.setPhoto(mePerson.getImage().getUrl());
      techGalleryUserDAO.update(techGalleryUser);
      log.info("User updated: " + mePerson.getDisplayName());
    }
  }

  /**
   * Is called by Google after the authorization process, with the authorization token incorporated
   * into the request. The URI must be registered on the developer's console and also be in the
   * client secrets file. This protects against another app with a stolen client secret from
   * stealing end-user's auth token.
   */
  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return OAuthUtils.getRedirectUri(req);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return OAuthUtils.newFlow();
  }

}
