package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;

/**
 * Transient object representing a technology recommendation.
 * 
 * @author eduardogf
 *
 */
public class TechnologyRecommendationTO implements Response {

  private Long id;

  private Boolean score;

  private TechnologyComment comment;

  private Boolean active;

  private TechGalleryUser recommender;

  private Technology technology;

  public Boolean getScore() {
    return score;
  }

  public void setScore(Boolean score) {
    this.score = score;
  }

  public TechnologyComment getComment() {
    return comment;
  }

  public void setComment(TechnologyComment comment) {
    this.comment = comment;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public TechGalleryUser getRecommender() {
    return recommender;
  }

  public void setRecommender(TechGalleryUser recommender) {
    this.recommender = recommender;
  }

  public Technology getTechnology() {
    return technology;
  }

  public void setTechnology(Technology technology) {
    this.technology = technology;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
