package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.Technology;

import java.util.List;

/**
 * Response with all technology entities.
 * 
 * @author felipers
 *
 */
public class TechnologiesResponse implements Response {

  /** list with all technologies. */
  List<Technology> technologies;

  public List<Technology> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(List<Technology> technologies) {
    this.technologies = technologies;
  }
}
