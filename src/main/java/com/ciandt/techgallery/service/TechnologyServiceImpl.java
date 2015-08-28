package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.MessageResponse;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologiesResponse;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * 
 * @author felipers
 *
 */
public class TechnologyServiceImpl implements TechnologyService {

  @Override
  public Response addTechnology(TechnologyResponse technology) throws InternalServerErrorException {
    throw new InternalServerErrorException("Not implemented");
    // return (new MessageResponse(400, "Not implemented"));
  }

  @Override
  public Response getTechnologies(TechnologiesResponse technologies)
      throws InternalServerErrorException {
    throw new InternalServerErrorException("Not implemented");
    // return (new MessageResponse(500, "Not implemented"));
  }

}
