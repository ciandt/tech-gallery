package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.TechnologyServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

/**
 * Endpoint controller class for Technology requests.
 * 
 * @author felipers
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID})
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
  public Response getTechnology(@Named("id") Long id) throws NotFoundException {
    return service.getTechnology(id);
  }

}
