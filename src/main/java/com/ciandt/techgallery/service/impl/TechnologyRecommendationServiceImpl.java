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
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.transformer.TechnologyRecommendationTransformer;

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

  private final TechnologyRecommendationDAO technologyRecommendationDAO =
      TechnologyRecommendationDAOImpl.getInstance();
  private final TechnologyService technologyService = TechnologyServiceImpl.getInstance();
  private final TechnologyRecommendationTransformer techRecTransformer =
      new TechnologyRecommendationTransformer();
  private final UserServiceTG userService = UserServiceTGImpl.getInstance();

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
  public TechnologyRecommendation addRecommendation(TechnologyRecommendation recommendation,
      User user) throws BadRequestException {
    try {
      final TechGalleryUser tgUser = userService.getUserByEmail(user.getEmail());
      recommendation.setRecommender(Ref.create(tgUser));
      return addNewRecommendation(recommendation, tgUser);
    } catch (final NotFoundException e) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

  /**
   * Adds a new recommendation to the datastore, invalidates the previous one.
   *
   * @param recommendation the recommendation to be added
   * @param technology the technology for which the recommendation was made
   * @param tgUser the user who made the recommendation
   * @return the updated recommendation, with id
   */
  private TechnologyRecommendation addNewRecommendation(TechnologyRecommendation recommendation,
      TechGalleryUser tgUser) {
    recommendation.setActive(true);
    recommendation.setRecommender(Ref.create(tgUser));
    final TechnologyRecommendation previousRec = technologyRecommendationDAO
        .findActiveByRecommenderAndTechnology(tgUser, recommendation.getTechnology().get());

    final Technology technology = recommendation.getTechnology().get();
    // Inactivate previous recommendation
    if (previousRec != null) {
      previousRec.setActive(false);
      previousRec.setInactivatedDate(new Date());
      technologyRecommendationDAO.update(previousRec);
      technologyService.removeRecomendationCounter(technology, previousRec.getScore());
    }
    recommendation.setId(technologyRecommendationDAO.add(recommendation).getId());
    technologyService.addRecomendationCounter(technology, recommendation.getScore());

    UserProfileServiceImpl.getInstance().handleRecommendationChanges(recommendation);
    return recommendation;
  }

  @Override
  public List<Response> getRecommendations(String technologyId, User user)
      throws BadRequestException, InternalServerErrorException {
    Technology technology;
    try {
      technology = technologyService.getTechnologyById(technologyId, user);
    } catch (final NotFoundException e) {
      return null;
    }
    final List<TechnologyRecommendation> recommendations =
        technologyRecommendationDAO.findAllActivesByTechnology(technology);
    final List<Response> recommendationTOs = new ArrayList<Response>();
    for (final TechnologyRecommendation recommendation : recommendations) {
      recommendationTOs.add(techRecTransformer.transformTo(recommendation));
    }
    return recommendationTOs;

  }

  @Override
  public TechnologyRecommendation getRecommendationByComment(TechnologyComment comment) {
    return technologyRecommendationDAO.findByComment(comment);
  }

  @Override
  public List<Response> getRecommendationsUpByTechnologyAndUser(String technologyId, User user)
      throws BadRequestException, InternalServerErrorException {
    return getRecommendationsByTechnologyUserAndScore(technologyId, user, true);
  }


  @Override
  public List<Response> getRecommendationsDownByTechnologyAndUser(String technologyId, User user)
      throws BadRequestException, InternalServerErrorException {
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
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  private List<Response> getRecommendationsByTechnologyUserAndScore(String technologyId, User user,
      Boolean score) throws BadRequestException, InternalServerErrorException {
    final List<Response> recommendationsUpTO = new ArrayList<Response>();
    for (final Response recommendation : getRecommendations(technologyId, user)) {
      final TechnologyRecommendationTO recommendationTO =
          (TechnologyRecommendationTO) recommendation;
      if (recommendationTO.getScore().equals(score)) {
        recommendationsUpTO.add(recommendationTO);
      }
    }
    return recommendationsUpTO;
  }

  @Override
  public Response deleteRecommendById(Long recommendId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {
    final TechnologyRecommendation recommendation =
        technologyRecommendationDAO.findById(recommendId);
    final TechGalleryUser techUser = userService.getUserByEmail(user.getEmail());

    validateDeletion(recommendId, recommendation, user, techUser);

    recommendation.setActive(false);
    technologyRecommendationDAO.update(recommendation);
    technologyService.removeRecomendationCounter(recommendation.getTechnology().get(),
        recommendation.getScore());

    UserProfileServiceImpl.getInstance().handleRecommendationChanges(recommendation);
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
   * @throws NotFoundException in case the information are not founded
   */
  private void validateDeletion(Long recommendId, TechnologyRecommendation recommendation,
      User user, TechGalleryUser techUser)
          throws BadRequestException, NotFoundException, InternalServerErrorException {
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
