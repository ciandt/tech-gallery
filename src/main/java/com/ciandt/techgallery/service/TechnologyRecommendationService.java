package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

import java.util.List;

public interface TechnologyRecommendationService {

  Response addRecommendation(TechnologyRecommendationTO recommendationTO, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

  TechnologyRecommendation getRecommendationByComment(TechnologyComment comment);

  List<Response> getRecommendations(String technologyId, User user);

  /**
   * Method that return the Recommendations Up by passed parameters.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 25/09/2015
   *
   * @param technologyId
   * @param user
   * 
   * @return List<Response>
   */
  List<Response> getRecommendationsUpByTechnologyAndUser(String technologyId, User user);

  /**
   * Method that return the Recommendations Down by passed parameters.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 25/09/2015
   *
   * @param technologyId
   * @param user
   * 
   * @return List<Response>
   */
  List<Response> getRecommendationsDownByTechnologyAndUser(String technologyId, User user);

  /**
   * Method to set the recommendation as inactive.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param recommendId
   * @param user
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws InternalServerErrorException
   * 
   * @return Response
   */
  Response deleteRecommendById(Long recommendId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException;
}
