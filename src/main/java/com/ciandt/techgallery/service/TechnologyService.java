package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

/**
 * Services for Technologies.
 * 
 * @author felipers
 *
 */
public interface TechnologyService {

  /**
   * Service for adding a technology.
   * 
   * @param technology json with technology info.
   * @return technology info or message error.
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  public Response addTechnology(TechnologyResponse technology)
      throws InternalServerErrorException, BadRequestException;

  /**
   * Service for getting all technologies.
   * 
   * @return technologies info or message error.
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  public Response getTechnologies() throws InternalServerErrorException, NotFoundException;

}
