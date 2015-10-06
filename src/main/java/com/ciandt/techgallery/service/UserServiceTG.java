package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;

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
   * Gets a TechGalleryUser by id.
   * 
   * @param id the user's id
   * @throws NotFoundException if the user is not found
   */
  Response getUsers() throws NotFoundException;

  /**
   * Adds a new user to Tech Gallery.
   * 
   * @param user the TechGallery user to be added
   * @throws BadRequestException when the user email parameter is missing
   */
  TechGalleryUser getUser(final Long id) throws NotFoundException;

  /**
   * Service for adding a user.
   * 
   * @param user json with user info.
   * @return user info or message error.
   * @throws BadRequestException
   */
  TechGalleryUser addUser(final TechGalleryUser user) throws BadRequestException;

  /**
   * Updates a user, with validation.
   * 
   * @throws BadRequestException in case of a missing parameter
   * @return the updated user
   */
  TechGalleryUser updateUser(final TechGalleryUser user) throws BadRequestException;

  /**
   * GET for getting an user by its login.
   * @param login the user's login
   * @return the user found
   */
  TechGalleryUser getUserByLogin(final String user) throws NotFoundException;

  /**
   * GET Calls the provider API passing a login to obtain user information.
   * 
   * @param userlogin the user login to pass to the provider API
   * @throws NotFoundException in case the user is not found on provider
   * @throws BadRequestException in case of JSON or URL error
   * @throws InternalServerErrorException if any IO exceptions occur
   */
  TechGalleryUser getUserFromProvider(final String user)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

  /**
   * Finds a TechGalleryUser by his/her email.
   * 
   * @param email the user's email
   * @throws NotFoundException if the user is not found
   */
  TechGalleryUser getUserByEmail(String email) throws NotFoundException;

  /**
   * Checks if user exists on provider, syncs with tech gallery's datastore. If user exists, adds to
   * TG's datastore (if not there). Returns the user.
   * 
   * @param userLogin userLogin
   * @return the user saved on the datastore
   */
  TechGalleryUser getUserSyncedWithProvider(String userLogin)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

  /**
   * This method should be executed whenever a user logs in It check whether the user exists on TG's
   * datastore and create them, if not. It also checks if the user's email has been changed and
   * update it, in case it was changed.
   * 
   * @param user A Google AppEngine API user
   * @return A response with the user data as it is on TG datastore
   */
  TechGalleryUser handleLogin(User user, HttpServletRequest req) throws NotFoundException,
      BadRequestException, InternalServerErrorException, IOException, OAuthRequestException;

}
