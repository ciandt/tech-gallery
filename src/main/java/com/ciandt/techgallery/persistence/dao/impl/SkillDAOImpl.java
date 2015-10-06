package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.SkillDAO;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

/**
 * SkillDAOImpl methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillDAOImpl extends GenericDAOImpl<Skill, Long>implements SkillDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static SkillDAOImpl instance;

  /*
   * Constructors --------------------------------------------
   */
  private SkillDAOImpl() {}

  public static SkillDAOImpl getInstance() {
    if (instance == null) {
      instance = new SkillDAOImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public Skill findByUserAndTechnology(TechGalleryUser user, Technology technology) {
    Objectify objectify = OfyService.ofy();
    Skill entity =
        objectify.load().type(Skill.class).filter(Skill.TECH_GALLERY_USER, Ref.create(user))
            .filter("technology", Ref.create(technology)).filter(Skill.ACTIVE, Boolean.TRUE).first()
            .now();

    return entity;
  }

}
