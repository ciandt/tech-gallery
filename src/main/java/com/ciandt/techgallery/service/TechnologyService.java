package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologiesResponse;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * 
 * @author felipers
 *
 */
public interface TechnologyService {

  /**
   * 
   * @param technology
   * @return
   * @throws InternalServerErrorException
   */
  public Response addTechnology(TechnologyResponse technology) throws InternalServerErrorException;

  /**
   * 
   * @return
   * @throws InternalServerErrorException
   */
  public Response getTechnologies() throws InternalServerErrorException;

}
