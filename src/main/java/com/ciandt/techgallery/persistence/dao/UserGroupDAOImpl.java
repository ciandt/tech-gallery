package com.ciandt.techgallery.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.UserGroup;
import com.googlecode.objectify.Objectify;

/**
 * User Group DAO Implementation class.
 * 
 * @author felipers
 *
 */
public class UserGroupDAOImpl implements UserGroupDAO {

  @Override
  public List<UserGroup> findAll() {
    Objectify objectify = OfyService.ofy();
    // field 'name' is indexed.
    List<UserGroup> groups = objectify.load().type(UserGroup.class).order("-name").list();
    // objectify.load().type(UserGroup.class).order("-name").list();
    return groups;
  }

  @Override
  public UserGroup findById(final Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserGroup findByName(final String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean addGroup(UserGroup group) {
    // Objectify ofy = OfyService.ofy();
    // ofy.save().entity(group).now();
    // ObjectifyService.ofy().save().entity(group).now();
    Objectify objectify = OfyService.ofy();
    objectify.save().entity(group).now();

    // if group ID != null, it was saved
    if (group.getId() != null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean updateGroup(UserGroup group) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean deleteGroup(UserGroup group) {
    // TODO Auto-generated method stub
    return false;
  }

}
