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
import com.ciandt.techgallery.service.TechnologyRecommendationService;
import com.ciandt.techgallery.service.TechnologyRecommendationServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyResponse;

import java.util.List;

@Api(name = "rest", version = "v1",
    clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class TechnologyRecommendationEndpoint {

  private TechnologyRecommendationService service = new TechnologyRecommendationServiceImpl();

  @ApiMethod(name = "getTechnologyRecommendations", path = "technology-recommendations",
      httpMethod = "get")
  public List<Response> getRecommendations(@Named("id") String technologyId,
      User user) throws InternalServerErrorException, BadRequestException, NotFoundException,
          OAuthRequestException {
    return service.getRecommendations(technologyId, user);
  }
}
