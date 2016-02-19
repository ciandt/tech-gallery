package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.TechnologyCommentService;
import com.ciandt.techgallery.service.impl.TechnologyCommentServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

/**
 * Endpoint controller class for Technology Comment requests.
 *
 * @author Felipe Ibrahim
 *
 */
@Api(name = "rest", version = "v1", clientIds = { Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID }, scopes = { Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE,
        Constants.PLUS_STREAM_WRITE })
public class TechnologyCommentEndpoint {

  private TechnologyCommentService service = TechnologyCommentServiceImpl.getInstance();

  /**
   * Endpoint for adding a Comment.
   *
   * @param json
   *          with Comment info.
   * @param user
   *          oauth user.
   * @return added commentary
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  @ApiMethod(name = "addComment", path = "technology-comment", httpMethod = "post")
  public TechnologyComment addComment(TechnologyComment comment, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    return service.addComment(comment, user);
  }

  /**
   * Endpoint for show Active Comments.
   *
   * @param json
   *          with Comment info.
   * @param user
   *          oauth user.
   * @return List of Commentaries
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  @ApiMethod(name = "getCommentsByTech", path = "technology-comment-show", httpMethod = "post")
  public Response getCommentsByTech(@Named("technologyId") String technologyId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.getCommentsByTech(technologyId, user);
  }

  /**
   * Endpoint to delete a comment.
   *
   * @param Id
   *          of a comment.
   * @param user
   *          oauth user.
   * @return deleted comment
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws OAuthRequestException
   *           in case of authentication problem
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  @ApiMethod(name = "deleteComment", path = "technology-comment-delete", httpMethod = "post")
  public TechnologyComment deleteComment(@Named("commentId") Long commentId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.deleteComment(commentId, user);
  }
}
