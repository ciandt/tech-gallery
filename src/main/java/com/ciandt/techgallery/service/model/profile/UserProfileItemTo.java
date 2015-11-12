package com.ciandt.techgallery.service.model.profile;

import java.util.List;

public class UserProfileItemTo {

  private String technologyName;

  private String companyRecommendation;

  private String technologyPhotoUrl;

  private Integer endorsementsCount;

  private Integer skillLevel;

  private List<SubItemCommentTo> comments;
  
  private RecomendationTo recommendation;

  public UserProfileItemTo() {}

  /**
   * All fields constructor.
   * 
   * @param technologyName the name of the Technology
   * @param companyRecommendation the company statement for the Technology
   * @param endorsementQuantity how many people is endorsed for the Technology
   * @param skillLevel the skill level of the Profile owner, for the Technology
   * @param comments the comments of the Profile owner, for the Technology
   */
  public UserProfileItemTo(String technologyName, String companyRecommendation,
      String technologyPhotoUrl, Integer endorsementQuantity, Integer skillLevel,
      List<SubItemCommentTo> comments) {
    super();
    this.technologyName = technologyName;
    this.companyRecommendation = companyRecommendation;
    this.technologyPhotoUrl = technologyPhotoUrl;
    this.endorsementsCount = endorsementQuantity;
    this.skillLevel = skillLevel;
    this.comments = comments;
  }

  public String getTechnologyName() {
    return technologyName;
  }

  public void setTechnologyName(String technologyName) {
    this.technologyName = technologyName;
  }

  public String getCompanyRecommendation() {
    return companyRecommendation;
  }

  public void setCompanyRecommendation(String companyRecommendation) {
    this.companyRecommendation = companyRecommendation;
  }

  public Integer getEndorsementsCount() {
    return endorsementsCount;
  }

  public void setEndorsementsCount(Integer endorsementQuantity) {
    this.endorsementsCount = endorsementQuantity;
  }

  public Integer getSkillLevel() {
    return skillLevel;
  }

  public void setSkillLevel(Integer skillLevel) {
    this.skillLevel = skillLevel;
  }

  public List<SubItemCommentTo> getComments() {
    return comments;
  }

  public void setComments(List<SubItemCommentTo> comments) {
    this.comments = comments;
  }

  public String getTechnologyPhotoUrl() {
    return technologyPhotoUrl;
  }

  public void setTechnologyPhotoUrl(String technologyPhotoUrl) {
    this.technologyPhotoUrl = technologyPhotoUrl;
  }

  public RecomendationTo getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(RecomendationTo recomendation) {
    this.recommendation = recomendation;
  }
}
