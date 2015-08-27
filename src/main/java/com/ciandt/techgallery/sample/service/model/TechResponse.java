package com.ciandt.techgallery.sample.service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TechnologyResponse entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class TechResponse extends Response {

  private Long id;
  private String name;
  private List<RecResponse> recommendations = new ArrayList<RecResponse>();

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

  public List<RecResponse> getRecommendationsResponse() {
    return recommendations;
  }

  public void setRecommendations(List<RecResponse> recommendations) {
    this.recommendations = recommendations;
  }
  
}
