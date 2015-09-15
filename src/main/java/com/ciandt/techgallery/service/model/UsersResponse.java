package com.ciandt.techgallery.service.model;

import java.util.List;

/**
 * Response with all users entities.
 * 
 * @author felipers
 *
 */
public class UsersResponse implements Response {

  /** list with all users. */
  List<UserResponse> users;

  public List<UserResponse> getUsers() {
    return users;
  }

  public void setUsers(List<UserResponse> users) {
    this.users = users;
  }

}
