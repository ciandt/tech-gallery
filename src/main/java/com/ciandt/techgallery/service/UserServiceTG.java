package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

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
  Response getUsers() throws NotFoundException;

  /**
   * Service for getting one user.
   * 
   * @param id entity id.
   * @return user info or message error.
   * @throws NotFoundException
   */
  Response getUser(final Long id) throws NotFoundException;

  /**
   * Service for adding a user.
   * 
   * @param user json with user info.
   * @return user info or message error.
   * @throws BadRequestException
   */
  Response addUser(final UserResponse user) throws BadRequestException;

  /**
   * Service for updating a user.
   * 
   * @param user json with user info.
   * @return user info or message error.
   * @throws BadRequestException
   */
  Response updateUser(final UserResponse user) throws BadRequestException;

  /**
   * Service for getting an User by its Login.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  Response getUserByLogin(final String user) throws NotFoundException;

  /**
   * Service for getting an User from an external provider by its Login.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   */
  Response getUserFromProvider(final String user)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

  /**
   * 
   */
  TechGalleryUser getUserByGoogleId(final String googleId)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

  /**
   * Seaches user on datastore by user's name and email
   * 
   * @param name user's name
   * @param email user's email
   * @return user response
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   */
  TechGalleryUser getUserByEmail(String email)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

  TechGalleryUser getUserSyncedWithProvider(String userLogin)
      throws NotFoundException, InternalServerErrorException;

  Response handleLogin(User user, HttpServletRequest req) throws NotFoundException,
      BadRequestException, InternalServerErrorException, IOException, OAuthRequestException;

}
