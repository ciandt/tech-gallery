package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.annotation.Id;

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
