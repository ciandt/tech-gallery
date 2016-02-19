package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.List;

/**
 * SkillDAO methods interface.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public interface SkillDAO extends GenericDAO<Skill, Long> {

  Skill findByUserAndTechnology(TechGalleryUser user, Technology technology);

  List<Skill> findByTechnology(Technology technology);

}
