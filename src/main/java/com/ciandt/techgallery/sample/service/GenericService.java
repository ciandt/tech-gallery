package com.ciandt.techgallery.sample.service;

import java.io.Serializable;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.dao.GenericObjectifyDAO;
import com.ciandt.techgallery.sample.persistence.model.BaseEntity;
import com.ciandt.techgallery.sample.service.model.Response;
import com.googlecode.objectify.Key;

/**
 * GenericServices methods.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public abstract class GenericService<R extends Response, T extends BaseEntity, ID extends Serializable>
    implements Service<R, T, ID> {

  protected GenericObjectifyDAO<T, ID> dao;

  public GenericService() {}

  public GenericService(GenericObjectifyDAO<T, ID> dao) {
    this.dao = dao;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> findAll() {
    List<T> listEntities = dao.findAll();

    return listEntities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Key<T> add(T entity) {
    Key<T> key = dao.add(entity);

    return key;
  }

}
