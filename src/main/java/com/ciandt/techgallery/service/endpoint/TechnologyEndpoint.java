package com.ciandt.techgallery.service.endpoint;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.TechnologyServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyFilter;
import com.ciandt.techgallery.service.model.TechnologyResponse;

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

  private TechnologyService service = new TechnologyServiceImpl();

  /**
   * Endpoint for adding a Technology.
   * 
   * @param json with technology info.
   * @return
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  @ApiMethod(name = "addTechnology", path = "technology", httpMethod = "post")
  public Response addTechnology(TechnologyResponse technology)
      throws InternalServerErrorException, BadRequestException {
    return service.addTechnology(technology);
  }

  /**
   * Endpoint for getting a list of Technologies.
   * 
   * @return
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  @ApiMethod(name = "getTechnologies", path = "technology", httpMethod = "get")
  public Response getTechnologies() throws InternalServerErrorException, NotFoundException {
    return service.getTechnologies();
  }

  /**
   * Endpoint for getting a Technology.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  @ApiMethod(name = "getTechnology", path = "technology/{id}", httpMethod = "get")
  public Response getTechnology(@Named("id") String id) throws NotFoundException {
    return service.getTechnology(id);
  }

  @ApiMethod(name = "findByFilter", path = "technology/search", httpMethod = "get")
  public Response findTechnologyByFilter(User user, @Named("titleContains") String titleContains,
      @Named("shortDescriptionContains") String shortDescriptionContains,
      @Named("recommendationIs") String recommendationIs,
      @Named("orderOptionIs") String orderOptionIs) throws ServiceException {
    return service.findTechnologiesByFilter(new TechnologyFilter(titleContains,
        shortDescriptionContains, recommendationIs, orderOptionIs), user);
  }

  @ApiMethod(name = "getOrderOptions", path = "technology/order-options", httpMethod = "get")
  public List<String> getOrderOptions(User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    return service.getOrderOptions(user);
  }

}
