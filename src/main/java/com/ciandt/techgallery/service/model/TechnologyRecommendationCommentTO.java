package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

public class TechnologyRecommendationCommentTO implements Response {

  private TechnologyRecommendation recommendation;
  private TechnologyComment comment;
  private Technology technology;

  public TechnologyRecommendation getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(TechnologyRecommendation recommendation) {
    this.recommendation = recommendation;
  }

  public TechnologyComment getComment() {
    return comment;
  }

  public void setComment(TechnologyComment comment) {
    this.comment = comment;
  }

  public Technology getTechnology() {
    return technology;
  }

  public void setTechnology(Technology technology) {
    this.technology = technology;
  }
}
