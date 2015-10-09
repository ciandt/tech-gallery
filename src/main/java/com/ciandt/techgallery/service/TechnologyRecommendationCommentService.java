package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

public interface TechnologyRecommendationCommentService {

  /**
   * Receives a wrapper which encapsulates a recommendation and a comment for a technology, verifies
   * if the comment is valid and saves them associated associated.
   *
   * @param recommendationTo the recommendation
   * @param commentTo the comment
   * @param technology the technology
   * @param user the logged in user
   * @return empty response
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  TechnologyRecommendation addRecommendationComment(TechnologyRecommendation recommendation,
      TechnologyComment comment, Technology technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException;

  /**
   * Responsable for the deletion of Comment and Recommendation.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param recommendationId Id of recommendation
   * @param commentId Id of commentary
   * @param user User
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  void deleteCommentAndRecommendationById(Long recommendationId, Long commentId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException;
}
