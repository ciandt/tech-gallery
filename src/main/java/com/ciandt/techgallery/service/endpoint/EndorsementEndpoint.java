package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * Endpoint controller class for Endorsements requests. Endorsements are used only for users.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID})
public class EndorsementEndpoint {

  @ApiMethod(name = "addEndorsement", path = "endorsement", httpMethod = "post")
  public Response addEndorsement() throws InternalServerErrorException {
    throw new InternalServerErrorException("Not yet implemented!");
  }

  @ApiMethod(name = "getEndorsements", path = "endorsement", httpMethod = "get")
  public Response getEndorsements() throws InternalServerErrorException {
    throw new InternalServerErrorException("Not yet implemented!");
  }

}
