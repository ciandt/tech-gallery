package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.TechnologyCommentService;
import com.ciandt.techgallery.service.TechnologyRecommendationCommentService;
import com.ciandt.techgallery.service.TechnologyRecommendationService;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

public class TechnologyRecommendationCommentServiceImpl
    implements TechnologyRecommendationCommentService {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyRecommendationCommentServiceImpl instance;
  private TechnologyRecommendationService recService =
      TechnologyRecommendationServiceImpl.getInstance();
  private TechnologyCommentService comService = TechnologyCommentServiceImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyRecommendationCommentServiceImpl() {}

  public static TechnologyRecommendationCommentServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyRecommendationCommentServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public TechnologyRecommendation addRecommendationComment(TechnologyRecommendation recommendation,
      TechnologyComment comment, Technology technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException {

    if (!isValidComment(comment)) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_CANNOT_BLANK.message());
    }
    comment.setTechnology(Ref.create(technology));
    comment = comService.addComment(comment, user);
    recommendation.setComment(Ref.create(comment));
    recommendation.setTechnology(Ref.create(technology));
    recommendation = recService.addRecommendation(recommendation, user);

    return recommendation;

  }

  /**
   * Validates if the comment is not blank and not null.
   * 
   * @param comment the comment wrapper
   * @return true if comment is valid, false otherwise
   */
  private boolean isValidComment(TechnologyComment comment) {
    return comment != null && comment.getComment() != null
        && !comment.getComment().trim().equals("");
  }

  @Override
  public void deleteCommentAndRecommendationById(Long recommendationId, Long commentId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException {
    comService.deleteComment(commentId, user);
    recService.deleteRecommendById(recommendationId, user);
  }

}
