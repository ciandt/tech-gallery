package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Skill entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
public class Skill extends BaseEntity<Long> {

  @Id
  private Long id;

  @Index
  @Load
  private Ref<Technology> technology;

  @Unindex
  private Integer value;

  @Index
  @Load
  private Ref<TechGalleryUser> user;

  @Unindex
  private Boolean inactive;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Ref<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Ref<Technology> technology) {
    this.technology = technology;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public Ref<TechGalleryUser> getUser() {
    return user;
  }

  public void setUser(Ref<TechGalleryUser> user) {
    this.user = user;
  }

  public Boolean getInactive() {
    return inactive;
  }

  public void setInactive(Boolean inactive) {
    this.inactive = inactive;
  }

  public TechGalleryUser getUserEntity() {
    if (user != null) {
      user.get();
    }

    return null;
  }

}
