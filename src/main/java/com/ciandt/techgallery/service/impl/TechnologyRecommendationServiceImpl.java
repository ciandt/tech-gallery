package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.TechnologyRecommendationService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.util.TechGalleryUserTransformer;
import com.ciandt.techgallery.service.util.TechnologyRecommendationTransformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class TechnologyRecommendationServiceImpl implements TechnologyRecommendationService {

  /*
   * Constants --------------------------------------------
   */
  private static final Logger log =
      Logger.getLogger(TechnologyRecommendationServiceImpl.class.getName());

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyRecommendationServiceImpl instance;

  private TechnologyRecommendationDAO technologyRecommendationDAO =
      TechnologyRecommendationDAOImpl.getInstance();
  private TechnologyService technologyService = TechnologyServiceImpl.getInstance();
  private TechnologyRecommendationTransformer techRecTransformer =
      new TechnologyRecommendationTransformer();
  private UserServiceTG userService = UserServiceTGImpl.getInstance();
  private TechGalleryUserTransformer userTransformer = new TechGalleryUserTransformer();

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyRecommendationServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 07/10/2015
   *
   * @return TechnologyRecommendationServiceImpl instance.
   */
  public static TechnologyRecommendationServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyRecommendationServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public Response addRecommendation(TechnologyRecommendationTO recommendationTO, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    TechGalleryUser tgUser = userService.getUserByEmail(user.getEmail());
    if (tgUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
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
   * Adds a new recommendation to the datastore, invalidates the previous one.
   * 
   * @param recommendation the recommendation to be added
   * @param technology to add at the recommendation
   * @param tgUser to add at the recommendation
   * @return the updated recommendation, with id
   */
  private TechnologyRecommendation addNewRecommendation(TechnologyRecommendation recommendation,
      Technology technology, TechGalleryUser tgUser) {
    recommendation.setTechnology(Ref.create(technology));
    recommendation.setActive(true);
    recommendation.setRecommender(Ref.create(tgUser));
    TechnologyRecommendation previousRec = technologyRecommendationDAO
        .findActiveByRecommenderAndTechnology(tgUser, recommendation.getTechnology().get());

    // Inactivate previous recommendation
    if (previousRec != null) {
      previousRec.setActive(false);
      previousRec.setInactivatedDate(new Date());
      technologyRecommendationDAO.update(previousRec);
      technologyService.removeRecomendationCounter(technology, previousRec.getScore());
    }
    recommendation.setId(technologyRecommendationDAO.add(recommendation).getId());
    technologyService.addRecomendationCounter(technology, recommendation.getScore());
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
    List<TechnologyRecommendation> recommendations =
        technologyRecommendationDAO.findAllActivesByTechnology(technology);
    List<Response> recommendationTOs = new ArrayList<Response>();
    for (TechnologyRecommendation recommendation : recommendations) {
      recommendationTOs.add(techRecTransformer.transformTo(recommendation));
    }
    return recommendationTOs;

  }

  @Override
  public TechnologyRecommendation getRecommendationByComment(TechnologyComment comment) {
    return technologyRecommendationDAO.findByComment(comment);
  }

  @Override
  public List<Response> getRecommendationsUpByTechnologyAndUser(String technologyId, User user) {
    return getRecommendationsByTechnologyUserAndScore(technologyId, user, true);
  }


  @Override
  public List<Response> getRecommendationsDownByTechnologyAndUser(String technologyId, User user) {
    return getRecommendationsByTechnologyUserAndScore(technologyId, user, false);
  }

  /**
   * Method to search for recommendations that has the technology, user and score passed.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 25/09/2015
   *
   * @param technologyId to find te recommendations
   * @param user to find the recommendations
   * @param score to filter the recommendations
   * 
   * @return recommendationsUpTO
   */
  private List<Response> getRecommendationsByTechnologyUserAndScore(String technologyId, User user,
      Boolean score) {
    List<Response> recommendationsUpTo = new ArrayList<Response>();
    for (Response recommendation : getRecommendations(technologyId, user)) {
      TechnologyRecommendationTO recommendationTo = (TechnologyRecommendationTO) recommendation;
      if (recommendationTo.getScore().equals(score)) {
        recommendationsUpTo.add(recommendationTo);
      }
    }
    return recommendationsUpTo;
  }

  @Override
  public Response deleteRecommendById(Long recommendId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {
    TechnologyRecommendation recommendation = technologyRecommendationDAO.findById(recommendId);
    TechGalleryUser techUser = userService.getUserByEmail(user.getEmail());

    validateDeletion(recommendId, recommendation, user, techUser);

    recommendation.setActive(false);
    technologyRecommendationDAO.update(recommendation);
    technologyService.removeRecomendationCounter(recommendation.getTechnology().get(),
        recommendation.getScore());
    return techRecTransformer.transformTo(recommendation);
  }

  /**
   * Responsable for validade the informations about the deletion.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param recommendId to use by validation
   * @param recommendation to use by validation
   * @param user to use by validation
   * @param techUser to use by validation
   * 
   * @throws BadRequestException in case the params are not correct
   * @throws InternalServerErrorException in case of internal error
   */
  private void validateDeletion(Long recommendId, TechnologyRecommendation recommendation,
      User user, TechGalleryUser techUser)
          throws BadRequestException, InternalServerErrorException {
    validateRecommend(recommendId, recommendation);
    validateUser(user, techUser);
    if (!recommendation.getRecommender().get().equals(techUser)) {
      throw new BadRequestException(ValidationMessageEnums.RECOMMEND_RECOMMENDER_ERROR.message());
    }
  }

  /**
   * Validation for User.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param user to be validated
   * @param techUser to be validated
   * 
   * @throws BadRequestException in case the params are not correct
   * @throws InternalServerErrorException in case of internal error
   */
  private void validateUser(User user, TechGalleryUser techUser)
      throws BadRequestException, InternalServerErrorException {
    log.info("Validating user to recommend");
    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

  /**
   * Validation for Recommend.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/09/2015
   *
   * @param recommendId to be validated
   * @param recommendation
   * 
   * @throws BadRequestException in case the params are not correct
   */
  private void validateRecommend(Long recommendId, TechnologyRecommendation recommendation)
      throws BadRequestException {
    log.info("Validating the recommend");
    if (recommendId == null) {
      throw new BadRequestException(ValidationMessageEnums.RECOMMEND_ID_CANNOT_BLANK.message());
    }
    if (recommendation == null) {
      throw new BadRequestException(ValidationMessageEnums.RECOMMEND_NOT_EXIST.message());
    }
  }
}
