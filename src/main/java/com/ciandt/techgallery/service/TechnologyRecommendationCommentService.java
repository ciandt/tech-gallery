package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

public interface TechnologyRecommendationCommentService {

  /**
   * Receives a wrapper which encapsulates a recommendation and a comment for a technology, verifies
   * if the comment is valid and saves them associated associated.
   * 
   * @param recommendationTO the recommendation
   * @param commentTO the comment
   * @param technology the technology
   * @param user the logged in user
   * @return
   * @throws BadRequestException
   * @throws InternalServerErrorException
   * @throws NotFoundException
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
   * @param recommendationTO
   * @param commentTO
   * @param user
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws OAuthRequestException
   */
  void deleteCommentAndRecommendationById(Long recommendationId, Long commentId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException;
}
