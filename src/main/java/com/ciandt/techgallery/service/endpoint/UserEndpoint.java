package com.ciandt.techgallery.service.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.impl.UserServiceTGImpl;
import com.ciandt.techgallery.service.model.UserResponse;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Endpoint controller class for User requests.
 *
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID}, scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE,
    Constants.PLUS_STREAM_WRITE})
public class UserEndpoint {

  private UserServiceTG service = UserServiceTGImpl.getInstance();

  @ApiMethod(name = "handleLogin", path = "handleLogin", httpMethod = "post")
  public TechGalleryUser handleLogin(@Named("timezone") Integer timezoneOffset, User user,
      HttpServletRequest req) throws NotFoundException, BadRequestException,
      InternalServerErrorException, IOException, OAuthRequestException {
    return service.handleLogin(timezoneOffset, user, req);
  }

  /**
   * Endpoint for getting a users from a user provider. The interface with the provider is made by
   * the service
   *
   * @param string to search on provider by name or login
   *
   * @return list of users
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  @ApiMethod(name = "usersAutoComplete", path = "users/autocomplete/{query}", httpMethod = "get")
  public List<UserResponse> usersAutoComplete(@Named("query") String query)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    return service.getUsersByPartialLogin(query);
  }

  /**
   * Endpoint for getting informations about the logged user.
   * 
   * @return Logged user.
   * @throws NotFoundException in case the information are not founded.
   */
  @ApiMethod(name = "getLoggedUser", path = "users/logged", httpMethod = "get")
  public TechGalleryUser getLoggedUser(User user) throws NotFoundException {
    return service.getUserByEmail(user.getEmail());
  }

  @ApiMethod(name = "saveUserPreference", path = "users/savePreference", httpMethod = "post")
  public TechGalleryUser saveUserPreference(
      @Named("postGooglePlusPreference") Boolean postGooglePlusPreference, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException, IOException,
      OAuthRequestException {
    return service.saveUserPreference(postGooglePlusPreference, user);
  }
}
