package com.ciandt.techgallery.sample.persistence.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Technology entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
public class Technology extends BaseEntity {

  @Index
  String name;

  List<Key<Recommendation>> recommendations = new ArrayList<Key<Recommendation>>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Key<Recommendation>> getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(List<Key<Recommendation>> recommendations) {
    this.recommendations = recommendations;
  }
}
