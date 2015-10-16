package com.ciandt.techgallery.service.transformer;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.SkillResponse;

/**
 * SkillConverter methods.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillConverter implements Transformer<Skill, SkillResponse> {

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  @Override
  public SkillResponse transformTo(Skill entity) {
    SkillResponse skillResponse = new SkillResponse();

    skillResponse.setId(entity.getId());
    skillResponse.setValue(entity.getValue());
    skillResponse.setTechnology(entity.getTechnologyEntity().getId());

    return skillResponse;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param transient entity
   * @return entity from datastore
   */
  @Override
  public Skill transformFrom(SkillResponse arg0) {
    Skill product = new Skill();

    product.setId(arg0.getId());
    product.setValue(arg0.getValue());
    if (arg0.getTechnology() != null) {
      Key<Technology> techKey = Key.create(Technology.class, arg0.getTechnology());
      product.setTechnology(Ref.create(techKey));
    } else {
      product.setTechnology(null);
    }
    if (arg0.getUser() != null) {
      Key<TechGalleryUser> userKey = Key.create(arg0.getUser());
      product.setTechGalleryUser(Ref.create(userKey));
    } else {
      product.setTechGalleryUser(null);
    }

    return product;
  }
}
