package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;

public interface TechnologyRecommendationCommentService {

  /**
   * Receives a wrapper which encapsulates a recommendation and a comment for a technology,
   * verifies if the comment is valid and saves them associated
   * associated.
   * @param recommendationTO the recommendation
   * @param commentTO the comment
   * @param technology the technology
   * @param user the logged in user
   * @return
   * @throws BadRequestException
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  public Response addRecommendationComment(TechnologyRecommendationTO recommendationTO,
      TechnologyCommentTO commentTO, TechnologyResponse technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException;
}
