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
public class TechnologySample extends SampleBaseEntity {

  @Index
  String name;

  List<Key<RecommendationSample>> recommendations = new ArrayList<Key<RecommendationSample>>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Key<RecommendationSample>> getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(List<Key<RecommendationSample>> recommendations) {
    this.recommendations = recommendations;
  }
}
