package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Entity class for a User Group.
 * 
 * Obs: It doesn´t follow the generic standards
 * 
 * @author felipers
 *
 */
@Entity
public class UserGroup {

  @Id
  private Long id;
  /** user's group name. */
  @Index
  private String name;

  public UserGroup() {}

  public UserGroup(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
