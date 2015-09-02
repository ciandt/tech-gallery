package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;

/**
 * Services for Users.
 * 
 * @author felipers
 *
 */
public interface UserServiceTG {

  /**
   * Service for getting all users.
   * 
   * @return users info or message error.
   * @throws NotFoundException
   */
  public Response getUsers() throws NotFoundException;

  /**
   * Service for getting one user.
   * 
   * @param id entity id.
   * @return user info or message error.
   * @throws NotFoundException
   */
  public Response getUser(final Long id) throws NotFoundException;

  /**
   * Service for adding a user.
   * 
   * @param user json with user info.
   * @return user info or message error.
   * @throws BadRequestException
   */
  public Response addUser(final UserResponse user) throws BadRequestException;

  /**
   * Service for updating a user.
   * 
   * @param user json with user info.
   * @return user info or message error.
   */
  public Response updateUser(final UserResponse user);

  /**
   * Service for getting an User by its Login.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  public Response getUserByLogin(final String user) throws NotFoundException;

}
