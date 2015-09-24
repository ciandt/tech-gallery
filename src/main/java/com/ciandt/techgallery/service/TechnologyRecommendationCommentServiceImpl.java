package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.utils.i18n.I18n;

public class TechnologyRecommendationCommentServiceImpl
    implements TechnologyRecommendationCommentService {

  private static final I18n i18n = I18n.getInstance();
  private TechnologyRecommendationService recService = new TechnologyRecommendationServiceImpl();
  private TechnologyCommentService comService = new TechnologyCommentServiceImpl();

  @Override
  public Response addRecommendationComment(TechnologyRecommendationTO recommendationTO,
      TechnologyCommentTO commentTO, TechnologyResponse technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException {
    Response response = null;
    if (isLoggedUserSameAsRecommender(user, recommendationTO, commentTO, technology)) {

      commentTO = (TechnologyCommentTO) comService.addComment(commentTO, user);
      recommendationTO =
          (TechnologyRecommendationTO) recService.addRecommendation(recommendationTO, user);

      return response;
    } else {
      throw new BadRequestException(i18n.t("Inconsistent users"));
    }

  }


  /**
   * Checks whether the logged in user is both the recommender and the commenter.
   * 
   * @param user logged in user
   * @param recommendationTO recommendation
   * @param commentTO comment
   * @param technology technology
   * @return true if the user is the same, false otherwise
   */
  private boolean isLoggedUserSameAsRecommender(User user,
      TechnologyRecommendationTO recommendationTO, TechnologyCommentTO commentTO,
      TechnologyResponse technology) {
    UserResponse recommender = recommendationTO.getRecommender();
    UserResponse commenter = commentTO.getAuthor();
    return user.getUserId().equals(recommender.getGoogleId())
        && user.getUserId().equals(commenter.getGoogleId());
  }

}
