package com.ciandt.techgallery.service.model;

public class TechnologyRecommendationCommentTO implements Response {

  private TechnologyRecommendationTO recommendationTO;
  private TechnologyCommentTO commentTO;
  private TechnologyResponse technologyTO;

  public TechnologyRecommendationTO getRecommendationTO() {
    return recommendationTO;
  }

  public void setRecommendationTO(TechnologyRecommendationTO recommendationTO) {
    this.recommendationTO = recommendationTO;
  }

  public TechnologyCommentTO getCommentTO() {
    return commentTO;
  }

  public void setCommentTO(TechnologyCommentTO commentTO) {
    this.commentTO = commentTO;
  }

  public TechnologyResponse getTechnologyTO() {
    return technologyTO;
  }

  public void setTechnologyTO(TechnologyResponse technologyTO) {
    this.technologyTO = technologyTO;
  }
}
