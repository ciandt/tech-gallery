package com.ciandt.techgallery.authorization;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.appengine.api.users.UserServiceFactory;

public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {

  private static final long serialVersionUID = 1L;

  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException {
    String email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
    resp.sendRedirect("/");
  }

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
  protected  initializeFlow() throws IOException {
    return GoogleAuthorizationCodeFlow
        .Builder(HTTP_TRANSPORT, JSON_FACTORY, getClientCredential(),
            Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline").build();// OAuthUtils.newFlow();
  }
}
