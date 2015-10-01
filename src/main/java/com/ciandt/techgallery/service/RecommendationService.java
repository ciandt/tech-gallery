package com.ciandt.techgallery.service;

import java.util.List;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

/**
 * Services for Recommendations.
 * 
 * @author Thulio Ribeiro
 *
 */
public interface RecommendationService {

  /**
   * Service for getting a list of possibles recommendations
   * @return 
   * @throws NotFoundException when entity is not found
   */
  public List<String> getRecommendations(User user) throws NotFoundException, BadRequestException;
  
}
