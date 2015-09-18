package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Entity class for Card.
 * 
 * Obs: It doesn´t follow the generic standards
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
public class Card {

  @Id
  private Long id;

  @Index
  private String name;
  
  @Unindex
  private String desc;
  
  @Unindex
  private String image;

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

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
