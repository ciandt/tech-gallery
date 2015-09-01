package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Technology entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
public class Technology extends BaseEntity<String> {

  @Id
  private String id;

  @Index
  private String name;

  @Unindex
  private String shortDescription;

  @Unindex
  private String description;

  @Unindex
  private String website;

  @Unindex
  private String author;

  @Unindex
  private String image;

  @Unindex
  private String citRecommendation;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCitRecommendation() {
    return citRecommendation;
  }

  public void setCitRecommendation(String citRecommendation) {
    this.citRecommendation = citRecommendation;
  }

}
