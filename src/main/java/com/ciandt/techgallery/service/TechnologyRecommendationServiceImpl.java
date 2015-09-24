package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.util.TechGalleryUserTransformer;
import com.ciandt.techgallery.service.util.TechnologyRecommendationTransformer;
import com.ciandt.techgallery.service.util.TechnologyTransformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TechnologyRecommendationServiceImpl implements TechnologyRecommendationService {

  private TechnologyRecommendationDAO dao = new TechnologyRecommendationDAOImpl();
  private TechnologyService technologyService = new TechnologyServiceImpl();
  private TechnologyRecommendationTransformer techRecTransformer =
      new TechnologyRecommendationTransformer();
  private UserServiceTG userService = new UserServiceTGImpl();
  private TechGalleryUserTransformer userTransformer = new TechGalleryUserTransformer();

  @Override
  public Response addRecommendation(TechnologyRecommendationTO recommendationTO, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    TechGalleryUser tgUser = userService.getUserByEmail(user.getEmail());
    if (tgUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
    UserResponse userResp = userTransformer.transformTo(tgUser);
    recommendationTO.setRecommender(userResp);
    Technology technology =
        technologyService.getTechnologyById(recommendationTO.getTechnology().getId());
    TechnologyRecommendation recommendation = techRecTransformer.transformFrom(recommendationTO);

    recommendation = addNewRecommendation(recommendation, technology, tgUser);

    return techRecTransformer.transformTo(recommendation);
  }

  /**
   * Adds a new recommendation to the datastore, invalidates the previous one
   * 
   * @param recommendation the recommendation to be added
   * @param technology
   * @param tgUser
   * @return the updated recommendation, with id
   */
  private TechnologyRecommendation addNewRecommendation(TechnologyRecommendation recommendation,
      Technology technology, TechGalleryUser tgUser) {
    recommendation.setTechnology(Ref.create(technology));
    recommendation.setActive(true);
    recommendation.setRecommender(tgUser);
    TechnologyRecommendation previousRec =
        dao.findActiveByRecommenderAndTechnology(tgUser, recommendation.getTechnology().get());

    // Inactivate previous recommendation
    if (previousRec != null) {
      previousRec.setActive(false);
      previousRec.setInactivatedDate(new Date());
      dao.update(previousRec);
    }
    recommendation.setId(dao.add(recommendation).getId());
    return recommendation;
  }

  @Override
  public List<Response> getRecommendations(String technologyId, User user) {
    Technology technology;
    try {
      technology = technologyService.getTechnologyById(technologyId);
    } catch (NotFoundException e) {
      return null;
    }
    List<TechnologyRecommendation> recommendations = dao.findAllActivesByTechnology(technology);
    List<Response> recommendationTOs = new ArrayList<Response>();
    for (TechnologyRecommendation recommendation : recommendations) {
      recommendationTOs.add(techRecTransformer.transformTo(recommendation));
    }
    return recommendationTOs;

  }

  @Override
  public TechnologyRecommendation getRecommendationByComment(TechnologyComment comment){
    return dao.findByComment(comment);
  }

}
