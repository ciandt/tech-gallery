package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.RecommendationEnums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Services for Recommendation Endpoint requests.
 * 
 * @author Thulio Ribeiro
 *
 */
public class RecommendationServiceImpl implements RecommendationService {

  TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();
  TechnologyDAO technologyDAO = new TechnologyDAOImpl();

  @Override
  public List<String> getRecommendations(User user) throws NotFoundException, BadRequestException {
    validateUser(user);
    List<RecommendationEnums> enumValues = Arrays.asList(RecommendationEnums.values());
    List<String> recommendations = new ArrayList<>();
    for (RecommendationEnums enumEntry : enumValues) {
      recommendations.add(enumEntry.message());
    }
    return recommendations;
  }

  /**
   * Validate the user logged in.
   * 
   * @param user info about user from google
   * @throws BadRequestException .
   */
  private void validateUser(User user) throws BadRequestException {

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    TechGalleryUser techUser = techGalleryUserDAO.findByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

}
