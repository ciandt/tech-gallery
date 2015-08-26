package com.ciant.techgallery.sample.service.model;

/**
 * RecommendationResponse entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class RecResponse {

  Long id;
  String score;

  TechResponse technology;

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

  public TechResponse getTechnology() {
    return technology;
  }

  public void setTechnology(TechResponse technology) {
    this.technology = technology;
  }

}
