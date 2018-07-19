package com.ciandt.techgallery;

import com.google.api.server.spi.Constant;

/**
 * Contains the client IDs and scopes for allowed clients consuming your API.
 */
public class Constants {
  public static final String SERVICE_ACCOUNT_CLIENT_ID = "101429468834662727296";//"r2EklsA7k8ODf91DMLYbhrwT";
  public static final String WEB_CLIENT_ID =
          "1091883615279-ehqv3bfpvtj47hplbsak68epgng6o24s.apps.googleusercontent.com";
  //"192387127763-mbfdnkegtcv169h4rsrn31fgn64mo21m.apps.googleusercontent.com";
  public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
  public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
  public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
  public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;

  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
  public static final String PLUS_SCOPE = "https://www.googleapis.com/auth/plus.me";
  public static final String PLUS_STREAM_WRITE = "https://www.googleapis.com/auth/plus.stream.write";

  public static final String CRON_MAIL_ACTIVITY_JOB = "/cron/activityresume";
  public static final String CRON_MAIL_ENDORSEMENT_JOB = "/cron/endorsementresume";

  public static final String POSITIVE_RECOMMENDATION_TEXT = "positivamente a tecnologia ";
  public static final String NEGATIVE_RECOMMENDATION_TEXT = "negativamente a tecnologia ";
  public static final String NEW_LINE = System.getProperty("line.separator");

  public static final String THUMBS_UP = "https://storage.googleapis.com/tech-gallery-assets/email/thumbs-up.png";
  public static final String THUMBS_DOWN = "https://storage.googleapis.com/tech-gallery-assets/email/thumbs-down.png";

  public static final String APP_NAME = "Tech Gallery";
  public static final String APP_EMAIL = "google-project@ciandt.com";

  public static final String TEMPLATES_FOLDER = "emailtemplates";

  public static final String PRODUCTION_PROPERTY = "Production";

  public static final String APPLICATION_VERSION_PROPERTY =
      "com.google.appengine.application.version";

  public static final String APPLICATION_ID_PROPERTY = "com.google.appengine.application.id";

  public static final String RUNTIME_ENVIRONMENT_PROPERTY =
      "com.google.appengine.runtime.environment";

  public static final String LINK_LOCALHOST = "http://localhost:8888/";

  public static final String LINK_PRODUCTION = "techgallery.ciandt.com";

  public static final String PATH_VIEW_TECH_HTML = "viewTech.html";

  public static final String EMAIL_CONTEXT_SINGLE = "te indicou em";

  public static final String EMAIL_CONTEXT_PLURAL = "te indicaram em";

}
