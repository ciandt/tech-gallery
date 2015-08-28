package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.TechnologyServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologiesResponse;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * Endpoint controller class for Technology requests.
 * 
 * @author felipers
 *
 */
@Api(name = "technology", version = "v1", clientIds = {Constants.WEB_CLIENT_ID})
public class TechnologyEndpoint {

  private TechnologyService service = new TechnologyServiceImpl();

  /**
   * Endpoint for adding a Technology.
   * 
   * @param json with technology info.
   * @return
   * @throws InternalServerErrorException 
   */
  @ApiMethod(name = "", httpMethod = "post")
  public Response addTechnology(TechnologyResponse technology) throws InternalServerErrorException {
    Response response = service.addTechnology(technology);
    return response;
  }

  /**
   * Endpoint for getting a list of Technologies.
   * 
   * @param technologies json with list of technologies.
   * @return
   * @throws InternalServerErrorException
   */
  @ApiMethod(name = "", httpMethod = "get")
  public Response getTechnologies(TechnologiesResponse technologies)
      throws InternalServerErrorException {
    Response response = service.getTechnologies(technologies);
    return response;
  }

}
