package com.ciandt.techgallery.sample.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.sample.persistence.model.UserGroup;
import com.googlecode.objectify.Objectify;

/**
 * User Group DAO Implementation class.
 * 
 * Obs: It doesn´t follow the generic standards
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
    return groups;
  }

  @Override
  public UserGroup findById(final Long id) {
    Objectify objectify = OfyService.ofy();
    UserGroup group = objectify.load().type(UserGroup.class).id(id).now();
    return group;
  }

  @Override
  public UserGroup findByName(final String name) {
    Objectify objectify = OfyService.ofy();
    UserGroup group = objectify.load().type(UserGroup.class).filter("name", name).list().get(0);
    return group;
  }

  @Override
  public boolean addGroup(UserGroup group) {
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
    Objectify objectify = OfyService.ofy();
    objectify.save().entity(group).now();
    return true;
  }

  @Override
  public boolean deleteGroup(UserGroup group) {
    Objectify objectify = OfyService.ofy();
    objectify.delete().entity(group).now();

    // if group ID = null, it was deleted
    if (group.getId() == null) {
      return true;
    } else {
      return false;
    }
  }

}
