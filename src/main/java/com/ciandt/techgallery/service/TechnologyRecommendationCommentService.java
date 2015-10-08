package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;

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
  Response addRecommendationComment(TechnologyRecommendationTO recommendationTo,
      TechnologyCommentTO commentTo, TechnologyResponse technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException;

  /**
   * Responsable for the deletion of Comment and Recommendation.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param recommendationTO Transient Object of recommendation
   * @param commentTO Transient Object of commentary
   * @param user User
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  void deleteCommentAndRecommendation(TechnologyRecommendationTO recommendationTO,
      TechnologyCommentTO commentTO, User user) throws InternalServerErrorException,
          BadRequestException, NotFoundException, OAuthRequestException;
}
