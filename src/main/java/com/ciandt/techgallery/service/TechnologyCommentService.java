package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.appengine.api.users.User;

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
  public Response addComment(TechnologyCommentTO comment, User user)
      throws InternalServerErrorException, BadRequestException;

}
