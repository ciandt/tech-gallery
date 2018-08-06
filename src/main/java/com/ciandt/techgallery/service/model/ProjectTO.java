package com.ciandt.techgallery.service.model;


/**
 * Response with a project entity.
 *
 * @author moizes
 *
 */
public class ProjectTO implements Response {

  /** project id. */
  private Long id;
  /** project name. */
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
