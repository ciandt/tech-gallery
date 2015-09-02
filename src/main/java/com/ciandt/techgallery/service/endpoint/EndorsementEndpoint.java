package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.EndorsementService;
import com.ciandt.techgallery.service.EndorsementServiceImpl;
import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

/**
 * Endpoint controller class for Endorsements requests. Endorsements are used only for users.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID})
public class EndorsementEndpoint {

  private EndorsementService service = new EndorsementServiceImpl();

  /**
   * Endpoint for adding or updating an Endorsement.
   * 
   * @param endorsement json with endorsement info.
   * @return
   * @throws InternalServerErrorException
   * @throws BadRequestException 
   */
  @ApiMethod(name = "addEndorsement", path = "endorsement", httpMethod = "post")
  public Response addEndorsement(EndorsementResponse endorsement)
      throws InternalServerErrorException, BadRequestException {
    return service.addOrUpdateEndorsement(endorsement);
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
  public Response getEndorsement(@Named("id") Long id) throws NotFoundException {
    return service.getEndorsement(id);
  }

}
