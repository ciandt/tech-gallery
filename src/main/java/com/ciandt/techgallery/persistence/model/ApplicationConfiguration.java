package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Cache;

/**
 * Application Settings entity.
 *
 * @author Marcos Fernandes 
 *
 */
@Cache
@Entity
public class ApplicationConfiguration extends BaseEntity<String> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String VALUE = "value";

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private String id;

  @Index
  private String value;

  /*
   * Getter's and Setter's --------------------------------------------
   */
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
