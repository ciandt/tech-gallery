package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Technology entity.
 * 
 * @author bliberal
 *
 */
@Entity
public class TechGalleryUser extends BaseEntity<Long> {

  @Id
  Long id;

  @Index
  private String gplusId;
  
  @Index
  private String name;

  @Index
  private String email;

  @Unindex
  private String photo;
  
  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getGplusId() {
    return gplusId;
  }

  public void setGplusId(String gplusId) {
    this.gplusId = gplusId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

}
