package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.SkillService;
import com.ciandt.techgallery.service.SkillServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.SkillResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;

/**
 * Endpoint controller class for Skill requests.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Api(name = "rest", version = "v1", clientIds = {Constants.WEB_CLIENT_ID})
public class SkillEndpoint {

  private SkillService service = new SkillServiceImpl();

  /**
   * Endpoint for adding a Skill.
   * 
   * @param json with skill info.
   * @return
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  @ApiMethod(name = "addSkill", path = "skill", httpMethod = "post")
  public Response addSkill(SkillResponse skill) throws InternalServerErrorException,
      BadRequestException {
    return service.createOrUpdateSkill(skill);
  }

}
