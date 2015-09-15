package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * Endpoint controller class for Recommendations requests. Recommendations are used only for
 * technologies.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID}, scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class RecommendationEndpoint {

  @ApiMethod(name = "addRecommendation", path = "recommendation", httpMethod = "post")
  public Response addRecommendation() throws InternalServerErrorException {
    throw new InternalServerErrorException("Not yet implemented!");
  }

  @ApiMethod(name = "getRecommendations", path = "recommendation", httpMethod = "get")
  public Response getRecommendations() throws InternalServerErrorException {
    throw new InternalServerErrorException("Not yet implemented!");
  }

}
