package com.ciandt.techgallery.service.model;

public class TechnologyRecommendationCommentTO implements Response {

  private TechnologyRecommendationTO recommendation;
  private TechnologyCommentTO comment;
  private TechnologyResponse technology;

  public TechnologyRecommendationTO getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(TechnologyRecommendationTO recommendationTO) {
    this.recommendation = recommendationTO;
  }

  public TechnologyCommentTO getComment() {
    return comment;
  }

  public void setComment(TechnologyCommentTO commentTO) {
    this.comment = commentTO;
  }

  public TechnologyResponse getTechnology() {
    return technology;
  }

  public void setTechnology(TechnologyResponse technologyTO) {
    this.technology = technologyTO;
  }
}
