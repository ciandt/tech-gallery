package com.ciandt.techgallery.persistence.model;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
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
  private Key<TechGalleryUser> endorser;

  @Index
  private Key<TechGalleryUser> endorsed;

  @Unindex
  private Date timestamp;

  @Index
  private Key<Technology> technology;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Key<TechGalleryUser> getEndorser() {
    return endorser;
  }

  public void setEndorser(Key<TechGalleryUser> endorser) {
    this.endorser = endorser;
  }

  public Key<TechGalleryUser> getEndorsed() {
    return endorsed;
  }

  public void setEndorsed(Key<TechGalleryUser> endorsed) {
    this.endorsed = endorsed;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Key<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Key<Technology> technology) {
    this.technology = technology;
  }

}
