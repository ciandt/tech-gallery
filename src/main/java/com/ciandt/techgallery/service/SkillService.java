package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.SkillResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * Services for Skills.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface SkillService {

  /**
   * Service for adding a technology.
   * 
   * @param skill json with skill info.
   * @return skill info or message error.
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  public Response createOrUpdateSkill(SkillResponse skill) throws InternalServerErrorException,
      BadRequestException;

}
