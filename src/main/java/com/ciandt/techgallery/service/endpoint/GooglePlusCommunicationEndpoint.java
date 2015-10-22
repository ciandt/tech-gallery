package com.ciandt.techgallery.service.endpoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.GooglePlusCommunicationService;
import com.ciandt.techgallery.service.impl.GooglePlusCommunicationServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

/**
 * Endpoint controller class for Google plus communications requests.
 * 
 * @author Thulio Ribeiro
 *
 */
@Api(name = "rest", version = "v1", clientIds = { Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID }, scopes = { Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE,
        Constants.PLUS_STREAM_WRITE })
public class GooglePlusCommunicationEndpoint {

  private GooglePlusCommunicationService service = GooglePlusCommunicationServiceImpl.getInstance();

  /**
   * Endpoint to post comment on google plus.
   * 
   * @param String
   *          with the content of the post.
   * 
   * @param user
   *          oauth user.
   * 
   * @param servletRequest
   *          current request.
   * 
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   * @throws IOException
   *           in case a failed or interrupted I/O operations.
   */
  @ApiMethod(name = "postComment", path = "googleplus/post", httpMethod = "post")
  public void postGooglePlus(@Named("content") String content, User user, HttpServletRequest req)
      throws InternalServerErrorException, BadRequestException, NotFoundException, IOException {
    service.postGooglePlus(content, user, req);
  }
}
