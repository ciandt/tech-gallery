package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;

/**
 * TechnologyCommentService Interface.
 * 
 * @author Felipe Ibrahim
 *
 */
public interface TechnologyCommentService {

  /**
   * Service for adding a technology.
   * 
   * @param comment json with comment.
   * @return comment or message error.
   * @throws InternalServerErrorException .
   * @throws BadRequestException .
   */
  TechnologyComment addComment(TechnologyComment comment, User user)
      throws InternalServerErrorException, BadRequestException;

  /**
   * Service to show all active comments for a technology.
   * 
   * @param techId technology entity id.
   * @return comment or message error.
   * @throws InternalServerErrorException .
   * @throws BadRequestException .
   */
  Response getCommentsByTech(final String techId, User user) throws InternalServerErrorException,
      BadRequestException, NotFoundException, OAuthRequestException;

  /**
   * Service to delete a comment.
   * 
   * @param commentId comment entity id.
   * @return comment or message error.
   * @throws InternalServerErrorException .
   * @throws BadRequestException .
   */
  TechnologyComment deleteComment(final Long commentId, User user) throws InternalServerErrorException,
      BadRequestException, NotFoundException, OAuthRequestException;
}
