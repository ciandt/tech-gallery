package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.SkillDAO;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.List;

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

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return SkillDAOImpl instance.
   */
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
    final Objectify objectify = OfyService.ofy();
    final Skill entity =
        objectify.load().type(Skill.class).filter(Skill.TECH_GALLERY_USER, Ref.create(user))
            .filter("technology", Ref.create(technology)).filter(Skill.ACTIVE, Boolean.TRUE).first()
            .now();

    return entity;
  }

  @Override
  public List<Skill> findByTechnology(Technology technology) {
    final Objectify objectify = OfyService.ofy();
    return objectify.load().type(Skill.class).filter(Skill.TECHNOLOGY, Ref.create(technology))
        .filter(Skill.ACTIVE, Boolean.TRUE).list();
  }

}
