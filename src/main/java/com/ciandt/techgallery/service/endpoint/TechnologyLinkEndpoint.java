package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechnologyLink;
import com.ciandt.techgallery.service.TechnologyLinkService;
import com.ciandt.techgallery.service.impl.TechnologyLinkServiceImpl;
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
 * Endpoint controller class for Technology Link requests.
 *
 * @author Felipe Ibrahim
 *
 */
@Api(name = "rest", version = "v1", clientIds = { Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID }, scopes = { Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE,
        Constants.PLUS_STREAM_WRITE })
public class TechnologyLinkEndpoint {

  private TechnologyLinkService service = TechnologyLinkServiceImpl.getInstance();

  /**
   * Endpoint for adding a Link.
   *
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  @ApiMethod(name = "addLink", path = "technology-link", httpMethod = "post")
  public TechnologyLink addLink(TechnologyLink link, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    return service.addLink(link, user);
  }

  /**
   * Endpoint for show Links.
   *
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  @ApiMethod(name = "getLinksByTech", path = "technology-link-show", httpMethod = "post")
  public Response getLinksByTech(@Named("technologyId") String technologyId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.getLinksByTech(technologyId, user);
  }

  /**
   * Endpoint to delete a link.
   *
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws OAuthRequestException
   *           in case of authentication problem
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  @ApiMethod(name = "deleteLink", path = "technology-link-delete", httpMethod = "post")
  public TechnologyLink deleteLink(@Named("linkId") Long linkId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.deleteLink(linkId, user);
  }
}
