package com.ciandt.techgallery.persistence.model.profile;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Serialize;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.transformer.profile.UserProfileItemTransformer;

import java.io.Serializable;
import java.util.HashMap;

@ApiTransformer(UserProfileItemTransformer.class)
public class UserProfileItem implements Comparable<UserProfileItem>, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String technologyName;

  private String technologyPhotoUrl;

  private String companyRecommendation;

  private Integer endorsementQuantity;

  private Integer skillLevel;

  @Serialize private HashMap<String, SubItemComment> comments;

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
    comments = new HashMap<String, SubItemComment>();
  }

  /**
   * Adds a new comment to the profile item.
   * 
   * @param originComment the key to the original TechnologyComment entity
   */
  public void addComment(TechnologyComment originComment) {
    if (comments == null) {
      comments = new HashMap<String, SubItemComment>();
    }
    comments.put(Key.create(originComment).getString(),
        new SubItemComment(originComment.getComment(), originComment.getTimestamp()));
  }

  /**
   * Removes a comment from the profile item.
   * 
   * @param commentKey the key of the comment to be removed
   */
  public void removeComment(Key<TechnologyComment> commentKey) {
    if (comments != null) {
      comments.remove(commentKey.getString());
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
   * @param number the number to be added to the counter
   * @return the final result
   */
  //TODO resolver com transação ou memcache. Isso nao resolve
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

  public HashMap<String, SubItemComment> getComments() {
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

