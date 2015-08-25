package com.ciandt.techgallery.sample.persistence.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Recommendation {

  @Id
  Long id;

  @Index
  String score;

  Key<Technology> technology;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
