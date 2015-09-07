package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.SkillResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.appengine.api.users.User;

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
  public Response addOrUpdateSkill(SkillResponse skill, User user)
      throws InternalServerErrorException, BadRequestException;

}
