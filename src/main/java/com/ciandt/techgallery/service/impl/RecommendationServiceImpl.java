package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.RecommendationService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.RecommendationEnums;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;

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

  UserServiceTG userService = UserServiceTGImpl.getInstance();
  private static RecommendationServiceImpl instance;

  private RecommendationServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 07/10/2015
   *
   * @return RecommendationServiceImpl instance.
   */
  public static RecommendationServiceImpl getInstance() {
    if (instance == null) {
      instance = new RecommendationServiceImpl();
    }
    return instance;
  }

  @Override
  public List<String> getRecommendations(User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    validateUser(user);
    final List<RecommendationEnums> enumValues = Arrays.asList(RecommendationEnums.values());
    final List<String> recommendations = new ArrayList<>();
    for (final RecommendationEnums enumEntry : enumValues) {
      recommendations.add(enumEntry.message());
    }
    return recommendations;
  }

  /**
   * Validate the user logged in.
   *
   * @param user info about user from google
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  private void validateUser(User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

}
