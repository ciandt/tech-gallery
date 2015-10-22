package com.ciandt.techgallery.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

/**
 * Services for google plus communications.
 * 
 * @author Thulio Ribeiro
 *
 */
public interface GooglePlusCommunicationService {

  /**
   * @param content
   *          String wiht the content of the post
   * @param user
   *          is the user that adding a post
   * @param req
   *          the current servlet request
   * 
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws IOException
   */
  void postGooglePlus(String content, User user, HttpServletRequest req)
      throws InternalServerErrorException, BadRequestException, NotFoundException, IOException;

}
