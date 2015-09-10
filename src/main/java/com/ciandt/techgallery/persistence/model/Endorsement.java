package com.ciandt.techgallery.persistence.model;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Endorsement entity.
 * 
 * @author felipers
 *
 */
@Entity
public class Endorsement extends BaseEntity<Long> {

  @Id
  private Long id;

  @Index
  @Load
  private Ref<TechGalleryUser> endorser;

  @Index
  @Load
  private Ref<TechGalleryUser> endorsed;

  @Unindex
  private Date timestamp;

  @Index
  private boolean active;

  @Index
  @Load
  private Ref<Technology> technology;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Ref<TechGalleryUser> getEndorser() {
    return endorser;
  }

  public void setEndorser(Ref<TechGalleryUser> endorser) {
    this.endorser = endorser;
  }

  public Ref<TechGalleryUser> getEndorsed() {
    return endorsed;
  }

  public void setEndorsed(Ref<TechGalleryUser> endorsed) {
    this.endorsed = endorsed;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Ref<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Ref<Technology> technology) {
    this.technology = technology;
  }

  public TechGalleryUser getEndorserEntity() {
    if (endorser != null) {
      return endorser.get();
    }

    return null;
  }

  public TechGalleryUser getEndorsedEntity() {
    if (endorsed != null) {
      return endorsed.get();
    }

    return null;
  }


  public Technology getTechnologyEntity() {
    if (technology != null) {
      return technology.get();
    }

    return null;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
