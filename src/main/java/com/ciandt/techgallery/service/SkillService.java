package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.SkillResponse;

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
  Response addOrUpdateSkill(SkillResponse skill, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException;

  /**
   * Service for getting an user skill.
   * 
   * @param techId technology id.
   * @param user oauth user.
   * @return skill info or message error.
   * @throws BadRequestException
   * @throws OAuthRequestException
   * @throws NotFoundException
   * @throws InternalServerErrorException
   */
  Response getUserSkill(String techId, User user) throws BadRequestException, OAuthRequestException,
      NotFoundException, InternalServerErrorException;

  /**
   * Service for getting a TechGalleyUser skill.
   * 
   * @param techId technology id.
   * @param user datastore user.
   * @return skill info or message error.
   * @throws BadRequestException
   * @throws OAuthRequestException
   * @throws NotFoundException
   * @throws InternalServerErrorException
   */
  Response getUserSkill(String techId, TechGalleryUser user) throws BadRequestException,
      OAuthRequestException, NotFoundException, InternalServerErrorException;
}
