package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.util.Date;

/**
 * TechnologyCommentTO.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentTO implements Response {

  private Long id;
  private String comment;
  private Date creation;
  private String technologyId;
  private TechGalleryUser user;

  public TechGalleryUser getUser() {
    return user;
  }

  public void setUser(TechGalleryUser user) {
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getCreation() {
    return creation;
  }

  public void setCreation(Date creation) {
    this.creation = creation;
  }

  public String getTechnologyId() {
    return technologyId;
  }

  public void setTechnologyId(String technologyId) {
    this.technologyId = technologyId;
  }
}
