package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

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
   * @throws BadRequestException
   */
  public Response addTechnology(TechnologyResponse technology)
      throws InternalServerErrorException, BadRequestException;

  /**
   * 
   * @return
   * @throws InternalServerErrorException
   * @throws NotFoundException 
   */
  public Response getTechnologies() throws InternalServerErrorException, NotFoundException;

}
