package com.ciandt.techgallery.service;

import java.util.Date;

import com.ciandt.techgallery.persistence.dao.SkillDAO;
import com.ciandt.techgallery.persistence.dao.SkillDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.SkillResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * Services for Skill Endpoint requests.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillServiceImpl implements SkillService {

  SkillDAO skillDAO = new SkillDAOImpl();
  TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();
  TechnologyDAO technologyDAO = new TechnologyDAOImpl();

  @Override
  public Response createOrUpdateSkill(SkillResponse skill) throws InternalServerErrorException,
      BadRequestException {

    // TODO felipegc utilizar o servico do eduardo de buscar o usuario no people e trazer o
    // techgallery
    if (skill == null) {
      throw new BadRequestException("Skill cannot be null.");
    }

    if (skill.getTechnology() == null || "".equals(skill.getTechnology().getId())) {
      throw new BadRequestException("Technology(id) cannot be null or blank.");
    }

    if (skill.getUser() == null || skill.getUser().getId() == null) {
      throw new BadRequestException("User(id) cannot be null or blank.");
    }

    if (skill.getValue() == null || skill.getValue() < 0 || skill.getValue() > 5) {
      throw new BadRequestException("Skill's value must be between 1 and 5");
    }

    TechGalleryUser techUser = techGalleryUserDAO.findById(skill.getUser().getId());
    if (techUser == null) {
      throw new BadRequestException("User doesn't exist.");
    }

    Technology technology = technologyDAO.findById(skill.getTechnology().getId());
    if (technology == null) {
      throw new BadRequestException("Technology doesn't exist.");
    }

    Skill skillEntity = skillDAO.findByUserAndTechnology(techUser, technology);

    // if there is a skillEntity, it is needed to inactivate it and create a new one
    if (skillEntity != null) {
      skillEntity.setInactivatedDate(new Date());
      skillEntity.setActive(Boolean.FALSE);
      skillDAO.update(skillEntity);
    }

    Skill newSkill = addNewSkill(skill, techUser, technology);

    SkillResponse skillResponse = new SkillResponse();
    skillResponse.setId(newSkill.getId());
    skillResponse.setValue(newSkill.getValue());
    skillResponse.setTechnology(newSkill.getTechnologyEntity());

    return skillResponse;
  }

  private Skill addNewSkill(SkillResponse skill, TechGalleryUser techUser, Technology technology) {
    Skill newSkill = new Skill();
    newSkill.setTechGalleryUser(Ref.create(techUser));
    newSkill.setTechnology(Ref.create(technology));
    newSkill.setValue(skill.getValue());
    newSkill.setActive(Boolean.TRUE);
    Key<Skill> newSkillKey = skillDAO.add(newSkill);
    newSkill.setId(newSkillKey.getId());

    return newSkill;
  }

}
