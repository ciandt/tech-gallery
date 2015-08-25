package com.ciandt.techgallery.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.persistence.model.UserGroup;

/**
 * UserGroupEndpoint Interface.
 * 
 * @author felipegc
 *
 */
public interface UserGroupEndpoint {

  public List<UserGroup> listUsers();

  public UserGroup insertUserGroup(UserGroup userGroup);
}
