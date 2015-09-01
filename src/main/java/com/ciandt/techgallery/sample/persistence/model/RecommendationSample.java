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
public class RecommendationSample extends SampleBaseEntity {

  @Index
  String score;

  Key<TechnologySample> technology;

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public Key<TechnologySample> getTechnology() {
    return technology;
  }

  public void setTechnology(Key<TechnologySample> technology) {
    this.technology = technology;
  }

}
