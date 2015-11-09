package com.ciandt.techgallery.service.endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.TechnologyRecommendationCommentService;
import com.ciandt.techgallery.service.impl.TechnologyRecommendationCommentServiceImpl;
import com.ciandt.techgallery.service.model.TechnologyRecommendationCommentTO;

@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID}, scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE,
    Constants.PLUS_STREAM_WRITE})
public class TechnologyRecommendationCommentEndpoint {

  private TechnologyRecommendationCommentService service =
      TechnologyRecommendationCommentServiceImpl.getInstance();

  /**
   * Endpoint for adding a recomendation and comment.
   * 
   * @param recCommentTo transient objetc for recomendationComment
   * @param user json with user informations
   * @return recComment
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  @ApiMethod(name = "addRecommendationComment", path = "recommendation-comment",
      httpMethod = "post")
  public TechnologyRecommendation addRecommendationComment(
      TechnologyRecommendationCommentTO recCommentTo, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    return service.addRecommendationComment(recCommentTo.getRecommendation(),
        recCommentTo.getComment(), recCommentTo.getTechnology(), user);
  }

  /**
   * Endpoint for deleting a comment and recomendation.
   * 
   * @param recommendId recommendation Id
   * @param commentId Commentary Id
   * @param user User
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  @ApiMethod(name = "deleteCommentAndRecommendation", path = "delete-recommendation-comment",
      httpMethod = "post")
  public void deleteCommentAndRecommendation(@Named("recommendId") Long recommendId,
      @Named("commentId") Long commentId, User user) throws InternalServerErrorException,
      BadRequestException, NotFoundException, OAuthRequestException {
    service.deleteCommentAndRecommendationById(recommendId, commentId, user);
  }

}
