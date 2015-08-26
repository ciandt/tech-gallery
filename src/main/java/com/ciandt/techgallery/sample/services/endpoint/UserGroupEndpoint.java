package com.ciandt.techgallery.sample.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.persistence.model.UserGroup;

/**
 * UserGroupEndpoint Interface.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface UserGroupEndpoint {

  public List<UserGroup> listUsers();

  public UserGroup insertUserGroup(UserGroup userGroup);
}
