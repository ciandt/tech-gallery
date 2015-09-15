package com.ciandt.techgallery.service.endpoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.UserServiceTGImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

/**
 * Endpoint controller class for User requests.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID}, scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class UserEndpoint {

  private UserServiceTG service = new UserServiceTGImpl();

  /**
   * Endpoint for adding a User.
   * 
   * @param user json with user info.
   * @return
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  @ApiMethod(name = "addUser", path = "user", httpMethod = "post")
  public Response addUser(UserResponse user) throws InternalServerErrorException,
      BadRequestException {
    return service.addUser(user);
  }

  /**
   * Endpoint for getting a list of Users.
   * 
   * @return
   * @throws NotFoundException
   */
  @ApiMethod(name = "getUsers", path = "user", httpMethod = "get")
  public Response getUsers() throws NotFoundException {
    return service.getUsers();
  }

  /**
   * Endpoint for getting a User.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  @ApiMethod(name = "getUser", path = "user/{id}", httpMethod = "get")
  public Response getUser(@Named("id") Long id) throws NotFoundException {
    return service.getUser(id);
  }

  /**
   * Endpoint for getting a User by its Login.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  @ApiMethod(name = "getUserByLogin", path = "userByLogin/{login}", httpMethod = "get")
  public Response getUserByLogin(@Named("login") String login) throws NotFoundException {
    return service.getUserByLogin(login);
  }

  /**
   * Endpoint for getting a User from a user provider. The interface with the provider is made by
   * the service
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  @ApiMethod(name = "getUserFromProvider", path = "userFromProvider/{login}", httpMethod = "get")
  public Response getUserFromProvider(@Named("login") String login) throws NotFoundException,
      BadRequestException, InternalServerErrorException {
    return service.getUserFromProvider(login);
  }

  @ApiMethod(name = "handleLogin", path = "handleLogin", httpMethod = "post")
  public Response handleLogin(User user, HttpServletRequest req) throws NotFoundException,
      BadRequestException, InternalServerErrorException, IOException, OAuthRequestException {
    return service.handleLogin(user, req);
  }


}
