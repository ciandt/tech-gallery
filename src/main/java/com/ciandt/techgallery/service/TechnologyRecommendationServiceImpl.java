package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.util.TechnologyRecommendationTransformer;
import com.ciandt.techgallery.service.util.TechnologyTransformer;

public class TechnologyRecommendationServiceImpl implements TechnologyRecommendationService {

  private TechnologyRecommendationDAO dao = new TechnologyRecommendationDAOImpl();
  private TechnologyRecommendationTransformer techRecTransformer =
      new TechnologyRecommendationTransformer();
  private UserServiceTG userService = new UserServiceTGImpl();
  private TechnologyTransformer techTransformer = new TechnologyTransformer();

  @Override
  public Response addRecommendation(TechnologyRecommendationTO recommendationTO, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    TechGalleryUser tgUser = userService.getUserByEmail(user.getEmail());
    TechnologyRecommendation recommendation = techRecTransformer.transformFrom(recommendationTO);
    TechnologyRecommendation previousRec =
        dao.findActiveByRecommenderAndTechnology(tgUser, recommendation.getTechnology());

    // Inactivate previous recommendation
    if (previousRec != null) {
      previousRec.setActive(false);
      dao.update(previousRec);
    }

    recommendation.setId(dao.add(recommendation).getId());
    return techRecTransformer.transformTo(recommendation);
  }
}
