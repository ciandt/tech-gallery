package com.ciandt.techgallery.service.model;

/**
 * Response with an user entity.
 * 
 * @author felipers
 *
 */
public class UserResponse implements Response {

  /** user id. */
  private Long id;
  /** user name */
  private String name;
  /** user email. */
  private String email;
  /** user photo url. */
  private String photo;
  /** user google id. */
  private String googleId;

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

}
