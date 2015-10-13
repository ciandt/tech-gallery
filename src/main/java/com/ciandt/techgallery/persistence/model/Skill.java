package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.transformer.SkillConverter;

/**
 * Skill entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
@ApiTransformer(SkillConverter.class)
public class Skill extends BaseEntity<Long> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String TECHNOLOGY = "technology";
  public static final String VALUE = "value";
  public static final String TECH_GALLERY_USER = "techGalleryUser";
  public static final String ACTIVE = "active";

  @Id
  private Long id;

  @Index
  @Load
  private Ref<Technology> technology;

  @Unindex
  private Integer value;

  @Index
  @Load
  private Ref<TechGalleryUser> techGalleryUser;

  @Index
  private Boolean active;

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

  public Ref<TechGalleryUser> getTechGalleryUser() {
    return techGalleryUser;
  }

  public void setTechGalleryUser(Ref<TechGalleryUser> techGalleryUser) {
    this.techGalleryUser = techGalleryUser;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  /**
   * Returns the entity for the referred technology.
   * @return Technology
   */
  public Technology getTechnologyEntity() {
    if (technology != null) {
      return technology.get();
    }
    return null;
  }

  /**
   * Returns the entity for the referred user.
   * @return TechGalleryUser
   */
  public TechGalleryUser getUserEntity() {
    if (techGalleryUser != null) {
      return techGalleryUser.get();
    }
    return null;
  }

}
