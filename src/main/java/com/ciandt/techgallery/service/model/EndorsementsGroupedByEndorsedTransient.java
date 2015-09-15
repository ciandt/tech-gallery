package com.ciandt.techgallery.service.model;

import java.util.List;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.sample.service.model.Response;

/**
 * TechnologyResponse entity.
 * 
 * @author Daniel Eduardo 
 *
 */
public class EndorsementsGroupedByEndorsedTransient extends Response {

  TechGalleryUser endorsed;
  
  Integer endorsedSkill;
  
  List<TechGalleryUser> endorsers;

  public TechGalleryUser getEndorsed() {
    return endorsed;
  }

  public void setEndorsed(TechGalleryUser endorsed) {
    this.endorsed = endorsed;
  }

  public List<TechGalleryUser> getEndorsers() {
    return endorsers;
  }

  public void setEndorsers(List<TechGalleryUser> endorsers) {
    this.endorsers = endorsers;
  }

  public Integer getEndorsedSkill() {
    return endorsedSkill;
  }

  public void setEndorsedSkill(Integer endorsedSkill) {
    this.endorsedSkill = endorsedSkill;
  }
  
}
