package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.util.Comparator;
import java.util.List;

/**
 * EndorsementsGroupedByEndorsedTransient entity.
 *
 * @author Daniel Eduardo
 *
 */
public class EndorsementsGroupedByEndorsedTransient
    implements Response, Comparator<EndorsementsGroupedByEndorsedTransient> {

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

  @Override
  public int compare(EndorsementsGroupedByEndorsedTransient o1,
      EndorsementsGroupedByEndorsedTransient o2) {
    if (o1.getEndorsers().size() != o2.getEndorsers().size()) {
      return Integer.compare(o2.getEndorsers().size(), o1.getEndorsers().size());
    } else {
      return o1.getEndorsed().getName().compareTo(o2.getEndorsed().getName());
    }
  }
}
