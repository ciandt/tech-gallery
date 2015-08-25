package com.ciandt.techgallery.sample.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

/**
 * GenericObjectFyDAO methods implementation.
 * 
 * @author felipegc
 *
 */
public class GenericObjectFyDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {
  private Class<T> clazz;
  
  public GenericObjectFyDAO(){
    clazz = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }
  
  @Override
  public List<T> findAll() {
    Objectify objectify = OfyService.ofy();
    List<T> entities = objectify.load().type(clazz).list();
    return entities;
  }
  
  @Override
  public T findById(Serializable id) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public Key<T> add(T entity) {
    Objectify objectify = OfyService.ofy();
    Key<T> key = objectify.save().entity(entity).now();
    
    return key;
  }

  @Override
  public boolean update(T entity) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean delete(T entity) {
    // TODO Auto-generated method stub
    return false;
  }

}
