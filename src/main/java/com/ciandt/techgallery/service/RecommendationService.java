package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import java.util.List;

/**
 * Services for Recommendations.
 * 
 * @author Thulio Ribeiro
 *
 */
public interface RecommendationService {

  /**
   * Service for getting a list of possibles recommendations.
   * 
   * @return List of recomendations
   * @throws NotFoundException when entity is not found
   * @throws InternalServerErrorException in case something goes wrong
   */
  List<String> getRecommendations(User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

}
