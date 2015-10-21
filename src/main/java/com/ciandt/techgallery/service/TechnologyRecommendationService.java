package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.Response;

import java.util.List;

public interface TechnologyRecommendationService {

  /**
   * Sets a recommender and calls addNewRecommendation.
   * 
   * @param recommendation the recommendation to add
   * @param user the user who made the recommendation
   * @return the saved recommendation
   * @throws BadRequestException in case the user is not properly informed
   */
  TechnologyRecommendation addRecommendation(TechnologyRecommendation recommendation, User user)
      throws BadRequestException;

  TechnologyRecommendation getRecommendationByComment(TechnologyComment comment);

  List<Response> getRecommendations(String technologyId, User user)
      throws BadRequestException, InternalServerErrorException;

  /**
   * Method that return the Recommendations Up by passed parameters.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 25/09/2015
   *
   * @param technologyId Technology ID
   * @param user User
   * 
   * @return List of Responses
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  List<Response> getRecommendationsUpByTechnologyAndUser(String technologyId, User user)
      throws BadRequestException, InternalServerErrorException;

  /**
   * Method that return the Recommendations Down by passed parameters.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 25/09/2015
   *
   * @param technologyId Technology ID
   * @param user User
   * 
   * @return List of Responses
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  List<Response> getRecommendationsDownByTechnologyAndUser(String technologyId, User user)
      throws BadRequestException, InternalServerErrorException;

  /**
   * Method to set the recommendation as inactive.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param recommendId Recomendation ID.
   * @param user Recomendation User.
   * @return Response
   * @throws BadRequestException in case a request with problem were made.
   * @throws NotFoundException in case the information are not founded
   * @throws InternalServerErrorException in case something goes wrong
   */
  Response deleteRecommendById(Long recommendId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException;
}
