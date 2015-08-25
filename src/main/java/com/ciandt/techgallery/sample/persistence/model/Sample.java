package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Sample extends BaseEntity {

  // @Id
  // private Long id;

  @Index
  private String name;

  // public Long getId() {
  // return id;
  // }
  //
  // public void setId(Long id) {
  // this.id = id;
  // }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
