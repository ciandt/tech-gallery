package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Sample entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
public class Sample extends SampleBaseEntity {

  @Index
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
