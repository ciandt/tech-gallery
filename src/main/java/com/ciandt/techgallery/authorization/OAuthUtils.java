package com.ciandt.techgallery.authorization;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.datastore.Entity;


public class OAuthUtils {

  // Client secret must be stored as a resource and included in the build.
  // Can never be committed along with code
  private static final String CLIENTSECRETS = "techgallerysecrets.json";// "edu-tech-gallery-secret.json";

  public static List<String> getScopes() {
    return SCOPES;
  }

  private static final String REDIRECT_URI = "/oauth2callback";
  private static final List<String> SCOPES = Arrays.asList(
      "https://www.googleapis.com/auth/plus.me",
      "https://www.googleapis.com/auth/plus.stream.write");
  private static GoogleCredential credential = new GoogleCredential();
  private static GoogleAuthorizationCodeFlow authorizationCodeFlow;

  /**
   * Modifies the request's uri to build another uri (callback servlet) to send the auth tokens
   * after the user's authorization
   * 
   * @param req user's request
   * @return uri to send the auth token
   */
  static String getRedirectUri(HttpServletRequest req) {
    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath(REDIRECT_URI);
    return url.build();
  }

  /**
   * Builds and returns an Authorization Flow with predefined scopes and access type
   * 
   * @return the new Authorization Flow
   * @throws IOException when the client secret is not found
   */
  static GoogleAuthorizationCodeFlow newFlow() throws IOException {
    HttpTransport httpTransport = new NetHttpTransport();
    JacksonFactory jsonFactory = new JacksonFactory();
    Reader reader =
        new InputStreamReader(OAuthUtils.class.getClassLoader().getResourceAsStream(CLIENTSECRETS));
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), reader);

    if (authorizationCodeFlow == null) {
      authorizationCodeFlow =
          new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
              .setDataStoreFactory(AppEngineDataStoreFactory.getDefaultInstance())
              .setApprovalPrompt("force")
              .build();
    }
    return authorizationCodeFlow;
  }

  /**
   * TODO comment this
   * 
   * @param user
   * @throws IOException
   */
  public static void refreshAccessToken(Entity user) throws IOException {
    HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    JsonFactory JSON_FACTORY = new JacksonFactory();
    Reader reader = new InputStreamReader(OAuthUtils.class.getResourceAsStream(CLIENTSECRETS));
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), reader);

    OAuthUtils.credential =
        new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
            .setClientSecrets(clientSecrets).build();

    OAuthUtils.credential.setRefreshToken((String) user.getProperty("refreshToken"));
    OAuthUtils.credential.refreshToken();
  }
}
