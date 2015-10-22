package com.ciandt.techgallery.service.endpoint;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.TechnologyFollowersService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.impl.TechnologyFollowersServiceImpl;
import com.ciandt.techgallery.service.impl.TechnologyServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Endpoint controller class for Technology requests.
 *
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1",
    clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    scopes = {Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE})
public class TechnologyEndpoint {

  private TechnologyService service = TechnologyServiceImpl.getInstance();
  private TechnologyFollowersService followersService =
      TechnologyFollowersServiceImpl.getInstance();

  /**
   * Endpoint for adding a Technology.
   *
   * @param json with technology info.
   * @return added technology
   * @throws InternalServerErrorException in case something goes wrong
   * @throws BadRequestException in case a request with problem were made.
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  @ApiMethod(name = "addTechnology", path = "technology", httpMethod = "post")
  public Technology addTechnology(Technology technology, User user)
      throws InternalServerErrorException, BadRequestException, IOException,
      GeneralSecurityException {
    return service.addTechnology(technology, user);
  }

  /**
   * Endpoint for getting a list of Technologies.
   *
   * @return list of technologies
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  @ApiMethod(name = "getTechnologies", path = "technology", httpMethod = "get")
  public Response getTechnologies(User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    return service.getTechnologies(user);
  }

  /**
   * Endpoint for getting a Technology.
   *
   * @param id entity id.
   * @return technology
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  @ApiMethod(name = "getTechnology", path = "technology/{id}", httpMethod = "get")
  public Technology getTechnology(@Named("id") String id, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    return service.getTechnologyById(id, user);
  }

  /**
   * Endpointfor gettint a technology by filters
   *
   * @param user User
   * @param titleContains technology title part.
   * @param shortDescriptionContains technology short description part.
   * @param recommendationIs technology Ci&T recomendation
   * @param orderOptionIs sort type for the list of technologies
   * @return list of technologies
   * @throws ServiceException in case of exception in service
   */
  @ApiMethod(name = "findByFilter", path = "technology/search", httpMethod = "get")
  public Response findTechnologyByFilter(User user,
      @Named("titleContains") @Nullable String titleContains,
      @Named("shortDescriptionContains") @Nullable String shortDescriptionContains,
      @Named("recommendationIs") @Nullable String recommendationIs,
      @Named("orderOptionIs") @Nullable String orderOptionIs) throws ServiceException {
    return service.findTechnologiesByFilter(new TechnologyFilter(titleContains,
        shortDescriptionContains, recommendationIs, orderOptionIs), user);
  }

  /**
   * Endpoint for getting order option enumerations.
   *
   * @param user User
   * @return list of enumerations
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  @ApiMethod(name = "getOrderOptions", path = "technology/order-options", httpMethod = "get")
  public List<String> getOrderOptions(User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    return service.getOrderOptions(user);
  }

  /**
   * Endpoint for adding or removing a follower from Technology.
   *
   * @param json with technology id.
   * @return added technology
   * @throws InternalServerErrorException in case something goes wrong
   * @throws BadRequestException in case a request with problem were made.
   * @throws NotFoundException in case the information are not founded.
   */
  @ApiMethod(name = "followTechnology", path = "technology/follow", httpMethod = "post")
  public Technology followTechnology(@Named("technologyId") String technologyId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {
    return followersService.followTechnology(technologyId, user);
  }
}
