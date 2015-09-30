package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;

public class TechnologyRecommendationCommentServiceImpl
    implements TechnologyRecommendationCommentService {

  private TechnologyRecommendationService recService = new TechnologyRecommendationServiceImpl();
  private TechnologyCommentService comService = new TechnologyCommentServiceImpl();

  @Override
  public Response addRecommendationComment(TechnologyRecommendationTO recommendationTO,
      TechnologyCommentTO commentTO, TechnologyResponse technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException {

    if (!isValidComment(commentTO)) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_CANNOT_BLANK.message());
    }
    commentTO.setTechnologyId(technology.getId());
    commentTO = (TechnologyCommentTO) comService.addComment(commentTO, user);
    recommendationTO.setComment(commentTO);
    recommendationTO.setTechnology(technology);
    recommendationTO =
        (TechnologyRecommendationTO) recService.addRecommendation(recommendationTO, user);

    return recommendationTO;

  }

  /**
   * Validates if the comment is not blank and not null.
   * 
   * @param comment the comment wrapper
   * @return true if comment is valid, false otherwise
   */
  private boolean isValidComment(TechnologyCommentTO comment) {
    return comment != null && comment.getComment() != null
        && !comment.getComment().trim().equals("");
  }

  @Override
  public void deleteCommentAndRecommendation(TechnologyRecommendationTO recommendationTO,
      TechnologyCommentTO commentTO, User user) throws InternalServerErrorException,
          BadRequestException, NotFoundException, OAuthRequestException {
    comService.deleteComment(commentTO.getId(), user);
    recService.deleteRecommendById(recommendationTO.getId(), user);
  }

}
