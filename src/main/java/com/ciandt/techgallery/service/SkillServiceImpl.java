package com.ciandt.techgallery.service;

import java.util.Date;
import java.util.logging.Logger;

import com.ciandt.techgallery.persistence.dao.SkillDAO;
import com.ciandt.techgallery.persistence.dao.SkillDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.SkillResponse;
import com.ciandt.techgallery.service.util.SkillConverter;
import com.ciandt.techgallery.utils.i18n.I18n;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * Services for Skill Endpoint requests.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillServiceImpl implements SkillService {

  private static final Logger log = Logger.getLogger(SkillServiceImpl.class.getName());
  private static final I18n i18n = I18n.getInstance();

  SkillDAO skillDAO = new SkillDAOImpl();
  TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();
  TechnologyDAO technologyDAO = new TechnologyDAOImpl();
  /** tech gallery user service for getting PEOPLE API user. */
  UserServiceTG userService = new UserServiceTGImpl();

  @Override
  public Response addOrUpdateSkill(SkillResponse skill, User user)
      throws InternalServerErrorException, BadRequestException {

    log.info("Starting creating or updating skill");

    validateInputs(skill, user);

    Technology technology = technologyDAO.findById(skill.getTechnology());
    TechGalleryUser techUser = techGalleryUserDAO.findByGoogleId(user.getUserId());
    Skill skillEntity = skillDAO.findByUserAndTechnology(techUser, technology);

    // if there is a skillEntity, it is needed to inactivate it and create a new one
    if (skillEntity != null) {
      log.info("Inactivating skill: " + skillEntity.getId());
      skillEntity.setInactivatedDate(new Date());
      skillEntity.setActive(Boolean.FALSE);
      skillDAO.update(skillEntity);
    }

    Skill newSkill = addNewSkill(skill, techUser, technology);
    SkillResponse ret = SkillConverter.fromEntityToTransient(newSkill);

    return ret;
  }

  /**
   * Validate inputs of SkillResponse.
   * 
   * @param skill inputs to be validate
   * @param user info about user from google
   * @throws BadRequestException
   */
  private void validateInputs(SkillResponse skill, User user) throws BadRequestException {

    log.info("Validating inputs of skill");

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    TechGalleryUser techUser = techGalleryUserDAO.findByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }

    if (skill == null) {
      throw new BadRequestException(ValidationMessageEnums.SKILL_CANNOT_BLANK.message());
    }

    if (skill.getValue() == null || skill.getValue() < 0 || skill.getValue() > 5) {
      throw new BadRequestException(ValidationMessageEnums.SKILL_RANGE.message());
    }

    if (skill.getTechnology() == null || skill.getTechnology().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BLANK.message());
    }

    Technology technology = technologyDAO.findById(skill.getTechnology());
    if (technology == null) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NOT_EXIST.message());
    }

  }

  private Skill addNewSkill(SkillResponse skill, TechGalleryUser techUser, Technology technology) {
    log.info("Adding new skill...");

    Skill newSkill = new Skill();
    newSkill.setTechGalleryUser(Ref.create(techUser));
    newSkill.setTechnology(Ref.create(technology));
    newSkill.setValue(skill.getValue());
    newSkill.setActive(Boolean.TRUE);
    Key<Skill> newSkillKey = skillDAO.add(newSkill);
    newSkill.setId(newSkillKey.getId());

    log.info("New skill added: " + newSkill.getId());

    return newSkill;
  }

  @Override
  public Response getUserSkill(String techId, User user) throws BadRequestException,
      OAuthRequestException, NotFoundException, InternalServerErrorException {
    // user google id
    String googleId;
    // user from techgallery datastore
    TechGalleryUser tgUser;
    // User from endpoint can't be null
    if (user == null) {
      throw new OAuthRequestException(i18n.t("OAuth error, null user reference!"));
    } else {
      googleId = user.getUserId();
    }

    // TechGalleryUser can't be null and must exists on datastore
    if (googleId == null || googleId.equals("")) {
      throw new NotFoundException(i18n.t("Current user was not found!"));
    } else {
      // get the TechGalleryUser from datastore or PEOPLE API
      tgUser = techGalleryUserDAO.findByGoogleId(googleId);
      // userService.getUserSyncedWithProvider(userEmail.replace("@ciandt.com", ""));
      if (tgUser == null) {
        throw new BadRequestException(i18n.t("Endorser user do not exists on datastore!"));
      }
    }

    // Technology can't be null
    Technology technology = technologyDAO.findById(techId);
    if (technology == null) {
      throw new BadRequestException(i18n.t("Technology do not exists!"));
    }
    Skill userSkill = skillDAO.findByUserAndTechnology(tgUser, technology);
    if (userSkill == null) {
      throw new NotFoundException(i18n.t("User skill do not exist!"));
    } else {
      return SkillConverter.fromEntityToTransient(userSkill);
    }
  }

  @Override
  public Response getUserSkill(String techId, TechGalleryUser user) throws BadRequestException,
      OAuthRequestException, NotFoundException, InternalServerErrorException {
    // User can't be null
    if (user == null) {
      throw new OAuthRequestException(i18n.t("Null user reference!"));
    }

    // Technology can't be null
    Technology technology = technologyDAO.findById(techId);
    if (technology == null) {
      throw new BadRequestException(i18n.t("Technology do not exists!"));
    }
    Skill userSkill = skillDAO.findByUserAndTechnology(user, technology);
    if (userSkill == null) {
      return null;
    } else {
      return SkillConverter.fromEntityToTransient(userSkill);
    }
  }

}
