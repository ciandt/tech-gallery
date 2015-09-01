package com.ciandt.techgallery.sample.service;

import java.io.Serializable;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.dao.GenericSampleObjectifyDAO;
import com.ciandt.techgallery.sample.persistence.model.SampleBaseEntity;
import com.ciandt.techgallery.sample.service.model.Response;
import com.googlecode.objectify.Key;

/**
 * GenericServices methods.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public abstract class GenericService<R extends Response, T extends SampleBaseEntity, ID extends Serializable>
    implements Service<R, T, ID> {

  protected GenericSampleObjectifyDAO<T, ID> dao;

  public GenericService() {}

  public GenericService(GenericSampleObjectifyDAO<T, ID> dao) {
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
