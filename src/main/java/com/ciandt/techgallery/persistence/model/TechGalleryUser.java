package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.util.TechGalleryUserTransformer;

/**
 * Technology entity.
 * 
 * @author bliberal
 *
 */
@Entity
@ApiTransformer(TechGalleryUserTransformer.class)
public class TechGalleryUser extends BaseEntity<Long> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String PHOTO = "photo";
  public static final String GOOGLE_ID = "googleId";

  @Id
  Long id;

  @Index
  private String name;

  @Index
  private String email;

  @Unindex
  private String photo;

  @Index
  private String googleId;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
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

  public String getGoogleId() {
    return googleId;
  }

  public void setGoogleId(String googleId) {
    this.googleId = googleId;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TechGalleryUser))
      return false;
    else
      return (this.getId().equals(((TechGalleryUser) obj).getId()));
  }

}
