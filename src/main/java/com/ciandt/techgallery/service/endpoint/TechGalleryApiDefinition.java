package com.ciandt.techgallery.service.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.AuthLevel;
import com.ciandt.techgallery.Constants;


@Api(name = "rest", version = "v1", description = "TechGallery API",
    authLevel = AuthLevel.REQUIRED,
    clientIds = {
        Constants.WEB_CLIENT_ID,
        Constants.API_EXPLORER_CLIENT_ID,
        Constants.SERVICE_ACCOUNT_CLIENT_ID
    },
    scopes = {
        Constants.EMAIL_SCOPE, 
        Constants.PLUS_SCOPE,
        Constants.PLUS_STREAM_WRITE
    }, 
    authenticators = TechGalleryAuthenticator.class)
public class TechGalleryApiDefinition {

    public static String getApiVersion() {
        return "v1";
    }

}