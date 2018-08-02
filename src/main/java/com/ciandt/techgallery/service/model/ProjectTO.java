package com.ciandt.techgallery.service.model;


/**
 * Response with a project entity.
 *
 * @author moizes
 *
 */
public class ProjectTO implements Response {

  /** project id. */
  private String id;
  /** project name. */
  private String name;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
