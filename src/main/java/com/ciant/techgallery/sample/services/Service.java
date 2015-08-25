package com.ciant.techgallery.sample.services;

import java.util.List;

import com.googlecode.objectify.Key;

/**
 * Service Interface.
 * 
 * @author felipegc
 *
 */
public interface Service<S, T, ID> {
  
  /**
   * Method that return the List of Entities.
   * 
   * @return list of samples.
   */
  public List<T> findAll();
  
  /**
   * Method that add Entity into datastore.
   * 
   * @param entity to be persisted.
   * @return key generated by datastore.
   */
  public Key<T> add(T entity);
}
