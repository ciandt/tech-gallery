package com.ciant.techgallery.sample.service.model;

public class RecommendationResponse {

  Long id;
  String score;

  TechnologyResponse technology;

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

  public TechnologyResponse getTechnology() {
    return technology;
  }

  public void setTechnology(TechnologyResponse technology) {
    this.technology = technology;
  }

}
