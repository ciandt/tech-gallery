package com.ciandt.techgallery.persistence.model.profile;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Key;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.enums.RecommendationEnums;
import com.ciandt.techgallery.service.transformer.profile.UserProfileItemTransformer;

import java.util.HashMap;

@ApiTransformer(UserProfileItemTransformer.class)
public class UserProfileItem implements Comparable<UserProfileItem> {

  private String technologyName;

  private RecommendationEnums companyRecommendation;

  private Integer endorsementQuantity;

  private Integer skillLevel;

  private HashMap<Key<TechnologyComment>, SubItemComment> comments;

  /**
   * Removes a comment from the profile item.
   * 
   * @param commentKey the key of the comment to be removed
   */
  public void removeComment(Key<TechnologyComment> commentKey) {
    if (comments != null) {
      comments.remove(commentKey);
    }
  }

  /**
   * Adds a new comment to the profile item.
   * 
   * @param originComment the key to the original TechnologyComment entity
   */
  public void addComment(TechnologyComment originComment) {
    if (comments == null) {
      comments = new HashMap<Key<TechnologyComment>, SubItemComment>();
    }
    comments.put(Key.create(originComment),
        new SubItemComment(originComment.getComment(), originComment.getTimestamp()));
  }

  public String getTechnologyName() {
    return technologyName;
  }

  public void setTechnologyName(String technologyName) {
    this.technologyName = technologyName;
  }

  public RecommendationEnums getCompanyRecommendation() {
    return companyRecommendation;
  }

  public void setCompanyRecommendation(RecommendationEnums companyRecommendation) {
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

  public HashMap<Key<TechnologyComment>, SubItemComment> getComments() {
    return comments;
  }

  /**
   * Items with endorsements come first. If equal, follows lexicographical order for the name of the
   * Technology.
   */
  @Override
  public int compareTo(UserProfileItem arg0) {
    int endorsementsComparison =
        this.getEndorsementQuantity().compareTo(arg0.getEndorsementQuantity());
    return endorsementsComparison != 0 ? endorsementsComparison
        : this.getTechnologyName().compareTo(arg0.getTechnologyName());
  }

}

