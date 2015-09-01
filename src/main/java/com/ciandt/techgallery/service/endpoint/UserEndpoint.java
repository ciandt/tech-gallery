package com.ciandt.techgallery.service.endpoint;

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

/**
 * Endpoint controller class for User requests.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID})
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

}
