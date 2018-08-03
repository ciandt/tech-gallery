package com.ciandt.techgallery.service.endpoint;

import java.util.List;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.TechnologyRecommendationService;
import com.ciandt.techgallery.service.impl.TechnologyRecommendationServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@ApiReference(TechGalleryApiDefinition.class)
public class TechnologyRecommendationEndpoint {

  private TechnologyRecommendationService service = TechnologyRecommendationServiceImpl.getInstance();

  @ApiMethod(name = "getTechnologyRecommendations", path = "technology-recommendations", httpMethod = "get")
  public List<Response> getTechRecommendations(@Named("id") String technologyId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.getRecommendations(technologyId, user);
  }

  @ApiMethod(name = "getRecommendationsUp", path = "technology-recommendations-up", httpMethod = "get")
  public List<Response> getTechRecommendationsUp(@Named("id") String technologyId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.getRecommendationsUpByTechnologyAndUser(technologyId, user);
  }

  @ApiMethod(name = "getRecommendationsDown", path = "technology-recommendations-down", httpMethod = "get")
  public List<Response> getTechRecommendationsDown(@Named("id") String technologyId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    return service.getRecommendationsDownByTechnologyAndUser(technologyId, user);
  }

  @ApiMethod(name = "deleteRecommendById", path = "technology-delete-recommendation", httpMethod = "get")
  public Response deleteTechRecommendById(@Named("recommendId") Long recommendId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {
    return service.deleteRecommendById(recommendId, user);
  }
}
