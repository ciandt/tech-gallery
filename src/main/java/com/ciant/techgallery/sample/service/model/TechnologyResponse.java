package com.ciant.techgallery.sample.service.model;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.model.Recommendation;

public class TechnologyResponse extends Response {

  Long id;
  String name;
  List<RecommendationResponse> recommendations = new ArrayList<RecommendationResponse>();

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

  public List<RecommendationResponse> getRecommendationsPojo() {
    return recommendations;
  }

  public void setRecommendations(List<RecommendationResponse> recommendations) {
    this.recommendations = recommendations;
  }

}
