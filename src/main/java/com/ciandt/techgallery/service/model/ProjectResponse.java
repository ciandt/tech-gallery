package com.ciandt.techgallery.service.model;

/**
 * SkillResponse.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class ProjectResponse implements Response {

  private Long id;
  private String name;

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
}
