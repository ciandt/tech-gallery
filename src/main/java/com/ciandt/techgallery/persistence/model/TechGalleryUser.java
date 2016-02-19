package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.transformer.TechGalleryUserTransformer;

import java.util.List;

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
  public static final String FOLLOWED_TECHNOLOGY_IDS = "followedTechnologyIds";
  public static final String TIMEZONE_OFFSET = "timezoneOffset";

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

  @Index
  private List<String> followedTechnologyIds;

  @Index
  private boolean admin;

  @Unindex
  private Boolean postGooglePlusPreference;
  
  @Unindex
  private Integer timezoneOffset;

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

  public Boolean getPostGooglePlusPreference() {
    return postGooglePlusPreference;
  }

  public void setPostGooglePlusPreference(Boolean postGooglePlusPreference) {
    this.postGooglePlusPreference = postGooglePlusPreference;
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

  public List<String> getFollowedTechnologyIds() {
    return followedTechnologyIds;
  }

  public void setFollowedTechnologyIds(List<String> followedTechnologyIds) {
    this.followedTechnologyIds = followedTechnologyIds;
  }

  public Integer getTimezoneOffset() {
    return timezoneOffset;
  }

  public void setTimezoneOffset(Integer timezoneOffset) {
    this.timezoneOffset = timezoneOffset;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public TechGalleryUser() {
    this.postGooglePlusPreference = Boolean.TRUE;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    TechGalleryUser other = (TechGalleryUser) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }
}
