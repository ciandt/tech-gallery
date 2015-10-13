package com.ciandt.techgallery.service.endpoint.profile;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.profile.UserProfileService;

@Api(name = "rest", version = "v1",
    clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class UserProfileEndpoint {

  private UserProfileService service = UserProfileServiceImpl.getInstance();

  @ApiMethod(name = "profile.get", path = "profile", httpMethod = "get")
  public UserProfile getUserProfileByEmail(@Named("email") String email) throws NotFoundException {
    return service.findUserProfileByEmail(email);
  }

}
