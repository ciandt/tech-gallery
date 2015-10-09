package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.Date;

/**
 * Response with an endorsement Entity.
 * 
 * @author felipers
 *
 */
public class EndorsementEntityResponse implements Response {

  private Long id;

  private TechGalleryUser endorser;

  private TechGalleryUser endorsed;

  private Date timestamp;

  private Technology technology;

  private boolean active;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TechGalleryUser getEndorser() {
    return endorser;
  }

  public void setEndorser(TechGalleryUser endorser) {
    this.endorser = endorser;
  }

  public TechGalleryUser getEndorsed() {
    return endorsed;
  }

  public void setEndorsed(TechGalleryUser endorsed) {
    this.endorsed = endorsed;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Technology getTechnology() {
    return technology;
  }

  public void setTechnology(Technology technology) {
    this.technology = technology;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

}
