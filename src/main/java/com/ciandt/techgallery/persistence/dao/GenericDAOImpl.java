package com.ciandt.techgallery.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.BaseEntity;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

/**
 * GenericDAOImpl methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class GenericDAOImpl<T extends BaseEntity<ID>, ID extends Serializable>
    implements GenericDAO<T, ID> {

  private Class<T> clazz;

  @SuppressWarnings("unchecked")
  public GenericDAOImpl() {
    clazz =
        (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
