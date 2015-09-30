package com.ciandt.techgallery.service.model;

/**
 * Response with all technology entities.
 * 
 * @author felipers
 *
 */
public class TechnologyFilter implements Response {

  /** string for search in title */
  private String titleContains;
  
  /** string for search in title */
  private String shortDescriptionContains;

  public String getTitleContains() {
    return titleContains;
  }

  public void setTitleContains(String titleContains) {
    this.titleContains = titleContains;
  }

  public String getShortDescriptionContains() {
    return shortDescriptionContains;
  }

  public void setShortDescriptionContains(String shortDescriptionContains) {
    this.shortDescriptionContains = shortDescriptionContains;
  }
  
}
