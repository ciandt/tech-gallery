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
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.service.EndorsementService;
import com.ciandt.techgallery.service.impl.EndorsementServiceImpl;
import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.Response;

/**
 * Endpoint controller class for Endorsements requests. Endorsements are used only for users.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1",
    clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class EndorsementEndpoint {

  private EndorsementService service = EndorsementServiceImpl.getInstance();

  /**
   * Endpoint for adding or updating an Endorsement.
   * 
   * @param endorsement json with endorsement info.
   * @return
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws OAuthRequestException
   */
  @ApiMethod(name = "addEndorsement", path = "endorsement", httpMethod = "post")
  public Endorsement addEndorsement(EndorsementResponse endorsement, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException {
    return service.addOrUpdateEndorsement(endorsement, user);
  }

  /**
   * Endpoint for adding or updating an Endorsement through Plus One button.
   * 
   * @param endorsement json with endorsement info.
   * @return
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws OAuthRequestException
   */
  @ApiMethod(name = "addEndorsementPlusOne", path = "endorsementPlusOne", httpMethod = "post")
  public Endorsement addEndorsementPlusOne(EndorsementResponse endorsement, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException {
    return service.addOrUpdateEndorsementPlusOne(endorsement, user);
  }

  /**
   * Endpoint for getting a list of Endorsements.
   * 
   * @return
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  @ApiMethod(name = "getEndorsements", path = "endorsement", httpMethod = "get")
  public Response getEndorsements() throws InternalServerErrorException, NotFoundException {
    return service.getEndorsements();
  }

  /**
   * Endpoint for getting an Endorsement.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  @ApiMethod(name = "getEndorsement", path = "endorsement/{id}", httpMethod = "get")
  public Endorsement getEndorsement(@Named("id") Long id) throws NotFoundException {
    return service.getEndorsement(id);
  }

  /**
   * Endpoint for getting all endorsements of a Technology.
   * 
   * @param id technology id.
   * @return
   * @throws NotFoundException
   * @throws InternalServerErrorException
   * @throws OAuthRequestException
   * @throws BadRequestException
   */
  @ApiMethod(name = "getEndorsementsByTech", path = "endorsement/tech/{id}", httpMethod = "get")
  public Response getEndorsementsByTech(@Named("id") String id, User user) throws NotFoundException,
      InternalServerErrorException, BadRequestException, OAuthRequestException {
    return service.getEndorsementsByTech(id, user);
  }

}
