package com.ciandt.techgallery.service.util;

import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.service.model.SkillResponse;

/**
 * SkillConverter methods.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillConverter {

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  public static SkillResponse fromEntityToTransient(Skill entity) {
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
  public Skill fromTransientToEntity(SkillResponse tranzient) {
    // TODO Auto-generated method stub
    return null;
  }
}
