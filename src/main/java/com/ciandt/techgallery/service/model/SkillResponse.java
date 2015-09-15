package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

/**
 * SkillResponse.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SkillResponse implements Response {

  private Long id;
  private String technology;
  private Integer value;
  private TechGalleryUser user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTechnology() {
    return technology;
  }

  public void setTechnology(String technology) {
    this.technology = technology;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public TechGalleryUser getUser() {
    return user;
  }

  public void setUser(TechGalleryUser user) {
    this.user = user;
  }

}
