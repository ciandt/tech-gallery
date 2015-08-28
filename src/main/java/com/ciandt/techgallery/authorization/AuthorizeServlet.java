package com.ciandt.techgallery.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;

public class AuthorizeServlet extends AbstractAppEngineAuthorizationCodeServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected String getRedirectUri(HttpServletRequest req)
            throws ServletException, IOException {
        return OAuthUtils.getRedirectUri(req);
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return OAuthUtils.newFlow();
    }

}