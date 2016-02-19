package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.util.Date;

/**
 * TechnologyCommentTO.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentTO implements Response {

  private Long id;
  private Long recommendationId;
  private Boolean recommendationScore;
  private String comment;
  private Date creation;
  private String technologyId;
  private TechGalleryUser author;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean isRecommendationScore() {
    return recommendationScore;
  }

  public void setRecommendationScore(Boolean recommendationScore) {
    this.recommendationScore = recommendationScore;
  }

  public TechGalleryUser getAuthor() {
    return author;
  }

  public void setAuthor(TechGalleryUser author) {
    this.author = author;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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

  public Long getRecommendationId() {
    return recommendationId;
  }

  public void setRecommendationId(Long recommendationId) {
    this.recommendationId = recommendationId;
  }
}
