package com.ciandt.techgallery.service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Response with an user entity.
 * 
 * @author felipers
 *
 */
@SuppressWarnings("serial")
public class UserResponse implements Response, Serializable {

  /** user id. */
  private Long id;
  /** user name. */
  private String name;
  /** user email. */
  private String email;
  /** user photo url. */
  private String photo;
  /** user is admin. */
  private boolean admin;
  /** Followed technologies. */
  private List<String> followedTechIds;
  /** save the preference of user. */
  private Boolean postGooglePlusPreference;
  
  private String login;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getPostGooglePlusPreference() {
    return postGooglePlusPreference;
  }

  public void setPostGooglePlusPreference(Boolean postGooglePlusPreference) {
    this.postGooglePlusPreference = postGooglePlusPreference;
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
    if(email!=null && email.contains("@")){
      setLogin(email.split("@")[0]);
    }
    this.email = email;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public List<String> getFollowedTechIds() {
    return followedTechIds;
  }

  public void setFollowedTechIds(List<String> followedTechIds) {
    this.followedTechIds = followedTechIds;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
}
