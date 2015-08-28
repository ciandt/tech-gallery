package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.annotation.Id;

/**
 * BaseEntity entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class BaseEntity {

  @Id
  Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
