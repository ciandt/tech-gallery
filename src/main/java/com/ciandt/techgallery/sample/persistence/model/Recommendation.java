package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Recommendation entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
public class Recommendation extends BaseEntity {

  @Index
  String score;

  Key<Technology> technology;

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public Key<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Key<Technology> technology) {
    this.technology = technology;
  }

}
