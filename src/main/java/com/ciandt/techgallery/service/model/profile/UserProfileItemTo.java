package com.ciandt.techgallery.service.model.profile;

import com.ciandt.techgallery.persistence.model.profile.SubItemComment;

import java.util.List;

public class UserProfileItemTo {

  private String technologyName;

  private String companyRecommendation;

  private Integer endorsementQuantity;

  private Integer skillLevel;

  private List<SubItemComment> comments;

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
      Integer endorsementQuantity, Integer skillLevel, List<SubItemComment> comments) {
    super();
    this.technologyName = technologyName;
    this.companyRecommendation = companyRecommendation;
    this.endorsementQuantity = endorsementQuantity;
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

  public Integer getEndorsementQuantity() {
    return endorsementQuantity;
  }

  public void setEndorsementQuantity(Integer endorsementQuantity) {
    this.endorsementQuantity = endorsementQuantity;
  }

  public Integer getSkillLevel() {
    return skillLevel;
  }

  public void setSkillLevel(Integer skillLevel) {
    this.skillLevel = skillLevel;
  }

  public List<SubItemComment> getComments() {
    return comments;
  }

  public void setComments(List<SubItemComment> comments) {
    this.comments = comments;
  }


}
