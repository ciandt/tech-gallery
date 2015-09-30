package com.ciandt.techgallery.service.model;

/**
 * Transient object representing a technology recommendation.
 * 
 * @author eduardogf
 *
 */
public class TechnologyRecommendationTO implements Response {

  private Long id;

  private Boolean score;

  private TechnologyCommentTO comment;

  private Boolean active;

  private UserResponse recommender;

  private TechnologyResponse technology;

  public Boolean getScore() {
    return score;
  }

  public void setScore(Boolean score) {
    this.score = score;
  }

  public TechnologyCommentTO getComment() {
    return comment;
  }

  public void setComment(TechnologyCommentTO comment) {
    this.comment = comment;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public UserResponse getRecommender() {
    return recommender;
  }

  public void setRecommender(UserResponse recommender) {
    this.recommender = recommender;
  }

  public TechnologyResponse getTechnology() {
    return technology;
  }

  public void setTechnology(TechnologyResponse technology) {
    this.technology = technology;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
