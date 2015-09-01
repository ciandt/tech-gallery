package com.ciandt.techgallery.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;

public class AuthorizeServlet extends AbstractAppEngineAuthorizationCodeServlet {

  private static final long serialVersionUID = 1L;

  /**
   * Gets the current user and their credentials 
   */
  @Override
  public void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException ,IOException {
    AuthorizationCodeFlow authFlow = initializeFlow();
    Credential credential = authFlow.loadCredential(getUserId(req));
    //DO STUFF HERE
        
    
  }
  /**
   * Is called by Google after the authorization process, with the authorization 
   * token incorporated into the request. The URI must be registered
   * on the developer's console and also be in the client secrets file. This protects
   * against another app with a stolen client secret from stealing end-user's auth token.
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
