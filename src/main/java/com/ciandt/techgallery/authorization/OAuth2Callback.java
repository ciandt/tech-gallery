package com.ciandt.techgallery.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException { 

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    System.out.println("Users API ID: " + user.getUserId());
    
    
    Plus plus = new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("Tech Gallery").build();
    Person mePerson = plus.people().get("me").execute();
    
    TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();
    
    TechGalleryUser techGalleryUser = techGalleryUserDAO.findByGplusId(mePerson.getId());
    if (techGalleryUser == null){
      techGalleryUser = new TechGalleryUser();
      techGalleryUser.setGplusId(mePerson.getId());
      techGalleryUser.setName(mePerson.getDisplayName());
      techGalleryUser.setPhoto(mePerson.getImage().getUrl());
      techGalleryUser.setEmail(mePerson.getEmails().get(0).toString());
      techGalleryUserDAO.add(techGalleryUser);
    } else{
      techGalleryUser.setPhoto(mePerson.getImage().getUrl());
      techGalleryUserDAO.update(techGalleryUser);
    }
    
    System.out.println("ID:\t" + mePerson.getId());
    System.out.println("Display Name:\t" + mePerson.getDisplayName());
    System.out.println("Image URL:\t" + mePerson.getImage().getUrl());
    System.out.println("Profile URL:\t" + mePerson.getUrl());

    resp.sendRedirect("/");
  }

  @Override
  protected void onError(HttpServletRequest req, HttpServletResponse resp,
      AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
    resp.setStatus(200);
    resp.addHeader("Content-Type", "text/html");
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return OAuthUtils.getRedirectUri(req);
  }

  @Override
  protected  AuthorizationCodeFlow initializeFlow() throws IOException {
    return OAuthUtils.newFlow();
  }
}
