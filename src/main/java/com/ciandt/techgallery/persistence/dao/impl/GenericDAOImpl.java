package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.GenericDAO;
import com.ciandt.techgallery.persistence.model.BaseEntity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * GenericDAOImpl methods implementation.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public class GenericDAOImpl<T extends BaseEntity<ID>, ID extends Serializable>
    implements GenericDAO<T, ID> {

  public static final String ACTIVE = "active";

  private Class<T> clazz;

  @SuppressWarnings("unchecked")
  public GenericDAOImpl() {
    clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
  }

  @Override
  public List<T> findAll() {
    Objectify objectify = OfyService.ofy();
    List<T> entities = objectify.load().type(clazz).list();
    if (entities == null || entities.size() <= 0) {
      return null;
    }
    return entities;
  }

  @Override
  public List<T> findAllActives() {
    Objectify objectify = OfyService.ofy();
    List<T> entities = objectify.load().type(clazz).filter(ACTIVE, Boolean.TRUE).list();
    if (entities == null || entities.size() <= 0) {
      return null;
    }
    return entities;
  }

  @Override
  public T findById(ID id) {
    Objectify objectify = OfyService.ofy();
    T entity = null;

    if (id instanceof Long) {
      entity = objectify.load().type(clazz).id((Long) id).now();
    } else if (id instanceof String) {
      entity = objectify.load().type(clazz).id((String) id).now();
    }
    return entity;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see com.google.swapbudget.persistence.dao.GenericDAO#findByProperty(java.lang.Object)
   */
  @Override
  public T findByProperty(String property, Object value) {
    Objectify objectify = OfyService.ofy();
    T entity = objectify.load().type(clazz).filter(property, value).first().now();
    return entity;
  }

  @Override
  public Key<T> add(T entity) {
    Objectify objectify = OfyService.ofy();
    Key<T> key = objectify.save().entity(entity).now();

    return key;
  }

  @Override
  public boolean update(T entity) {
    Objectify objectify = OfyService.ofy();
    objectify.save().entity(entity).now();
    return true;
  }

  @Override
  public boolean delete(T entity) {
    Objectify objectify = OfyService.ofy();
    objectify.delete().entity(entity).now();

    // if group ID = null, it was deleted
    if (entity.getId() == null) {
      return true;
    } else {
      return false;
    }
  }

}
