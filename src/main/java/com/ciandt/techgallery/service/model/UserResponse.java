package com.ciandt.techgallery.service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Response with an user entity.
 * 
 * @author felipers
 *
 */
public class UserResponse implements Response, Serializable {

  /** user id. */
  private Long id;
  /** user name. */
  private String name;
  /** user email. */
  private String email;
  /** user photo url. */
  private String photo;
  /** user google id. */
  private String googleId;
  /** Followed technologies. */
  private List<String> followedTechIds;

  public Long getId() {
    return id;
  }

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

  public List<String> getFollowedTechIds() {
    return followedTechIds;
  }

  public void setFollowedTechIds(List<String> followedTechIds) {
    this.followedTechIds = followedTechIds;
  }
}
