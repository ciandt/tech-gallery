package com.ciandt.techgallery.persistence.model.profile;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Load;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.transformer.profile.UserProfileItemTransformer;

import java.util.HashSet;
import java.util.Set;

@ApiTransformer(UserProfileItemTransformer.class)
public class UserProfileItem implements Comparable<UserProfileItem> {

  private String technologyName;

  private String technologyPhotoUrl;

  private String companyRecommendation;

  private Integer endorsementQuantity;

  private Integer skillLevel;

  @Load
  private Set<Ref<TechnologyComment>> comments = new HashSet<Ref<TechnologyComment>>();

  public UserProfileItem() {}

  /**
   * Construct a profile item from a Technology. Only the technology name, recommendation and image
   * url are set.
   * 
   * @param technology the technology related to this item
   */
  public UserProfileItem(Technology technology) {
    setTechnologyName(technology.getName());
    setCompanyRecommendation(technology.getRecommendation());
    setTechnologyPhotoUrl(technology.getImage());
    endorsementQuantity = 0;
    setSkillLevel(0);
  }

  /**
   * Adds a new comment to the profile item.
   * 
   * @param originComment the key to the original TechnologyComment entity
   */
  public void addComment(TechnologyComment originComment) {
    if (comments == null) {
      comments = new HashSet<Ref<TechnologyComment>>();
    }
    comments.add(Ref.create(originComment));
  }

  /**
   * Removes a comment from the profile item.
   * 
   * @param originComment the key of the comment to be removed
   */
  public void removeComment(TechnologyComment originComment) {
    if (comments != null) {
      comments.remove(Ref.create(originComment));
    }
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


  public String getTechnologyPhotoUrl() {
    return technologyPhotoUrl;
  }

  public void setTechnologyPhotoUrl(String technologyPhotoUrl) {
    this.technologyPhotoUrl = technologyPhotoUrl;
  }

  public void setCompanyRecommendation(String companyRecommendation) {
    this.companyRecommendation = companyRecommendation;
  }

  /**
   * Adds a number (positive or negative) to the endorsements counter and returns the result.
   * 
   * @param number the number to be added to the counter
   * @return the final result
   */
  // TODO this method should run under a transaction to be safe from race conditions
  public synchronized Integer addToEndorsementsCounter(Integer number) {
    this.endorsementQuantity += number;
    return this.endorsementQuantity;
  }

  public Integer getEndorsementQuantity() {
    return addToEndorsementsCounter(0);
  }


  public Integer getSkillLevel() {
    return skillLevel;
  }

  public void setSkillLevel(Integer skillLevel) {
    this.skillLevel = skillLevel;
  }

  public Set<Ref<TechnologyComment>> getComments() {
    return comments;
  }

  /**
   * Items with more endorsements come first. If equal, follows lexicographical order for the name
   * of the Technology.
   * 
   * @param arg0 the other item to be compared with returns -1 if this item has more endorsements, 0
   *        if the quantities are the same, +1 if the other item has more endorsements
   */
  @Override
  public int compareTo(UserProfileItem arg0) {
    int endorsementsComparison =
        arg0.getEndorsementQuantity().compareTo(this.getEndorsementQuantity());
    return endorsementsComparison != 0 ? endorsementsComparison
        : this.getTechnologyName().compareTo(arg0.getTechnologyName());
  }

}

