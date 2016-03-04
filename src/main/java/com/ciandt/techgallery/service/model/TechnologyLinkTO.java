package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.util.Date;

/**
 * TechnologyLinkTO.
 *
 * @author Sidharta Noleto
 *
 */
public class TechnologyLinkTO implements Response {

  private Long id;
  private String description;
  private String link;
  private Date creation;
  private String technologyId;
  private TechGalleryUser author;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TechGalleryUser getAuthor() {
    return author;
  }

  public void setAuthor(TechGalleryUser author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }


  public Date getCreation() {
    return creation;
  }

  public void setCreation(Date creation) {
    this.creation = creation;
  }

  public String getTechnologyId() {
    return technologyId;
  }

  public void setTechnologyId(String technologyId) {
    this.technologyId = technologyId;
  }

}
