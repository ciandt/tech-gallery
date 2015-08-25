package com.ciant.techgallery.sample.services;

import java.io.Serializable;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.dao.GenericObjectFyDAO;
import com.ciandt.techgallery.sample.persistence.model.BaseEntity;
import com.ciant.techgallery.sample.service.model.Response;
import com.ciant.techgallery.sample.service.model.TechnologyResponse;
import com.googlecode.objectify.Key;

/**
 * GenericServices methods.
 * 
 * @author felipegc
 *
 */
public abstract class GenericService<R extends Response, T extends BaseEntity, ID extends Serializable>
    implements Service<R, T, ID> {

  private GenericObjectFyDAO<T, ID> dao;

  public GenericService() {}

  public GenericService(GenericObjectFyDAO<T, ID> dao) {
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

  public abstract R add(R pojo);

}
