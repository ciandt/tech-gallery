package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

/**
 * SkillDAOImpl methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillDAOImpl extends GenericDAOImpl<Skill, Long> implements SkillDAO {

  @Override
  public Skill findByUserAndTechnology(TechGalleryUser user, Technology technology) {
    Objectify objectify = OfyService.ofy();
    Skill entity =
        objectify.load().type(Skill.class).filter("techGalleryUser", Ref.create(user))
            .filter("technology", Ref.create(technology)).filter("active", Boolean.TRUE).first()
            .now();

    return entity;
  }

}
