package com.ciandt.techgallery.sample.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.sample.persistence.model.UserGroup;

/**
 * User Group DAO methods.
 * 
 * Obs: It doesn´t follow the generic standards
 * 
 * @author felipers
 *
 */
public interface UserGroupDAO {

  /**
   * Method that return a list with all User Group.
   * 
   * @return list of user group.
   */
  public List<UserGroup> findAll();

  /**
   * Method that return a User Group by its Id.
   * 
   * @param id user group id.
   * @return user group entity.
   */
  public UserGroup findById(final Long id);

  /**
   * Method that return a User Group by its name.
   * 
   * @param name user group name.
   * @return user group entity.
   */
  public UserGroup findByName(final String name);

  /**
   * Method that adds a new User Group entity.
   * 
   * @param group user group entity.
   * @return success or failure.
   */
  public boolean addGroup(final UserGroup group);

  /**
   * Method that updates a User Group entity.
   * 
   * @param group user group entity.
   * @return success or failure.
   */
  public boolean updateGroup(final UserGroup group);

  /**
   * Method that deletes a User Group entity.
   * 
   * @param group group user group entity.
   * @return success or failure.
   */
  public boolean deleteGroup(final UserGroup group);
}
