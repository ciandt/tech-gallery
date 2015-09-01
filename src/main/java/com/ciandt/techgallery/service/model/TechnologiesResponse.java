package com.ciandt.techgallery.service.model;

import java.util.List;

/**
 * Response with all technology entities.
 * 
 * @author felipers
 *
 */
public class TechnologiesResponse implements Response {

  /** list with all technologies. */
  List<TechnologyResponse> technologies;

  public List<TechnologyResponse> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(List<TechnologyResponse> technologies) {
    this.technologies = technologies;
  }
}
