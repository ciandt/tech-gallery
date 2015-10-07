package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.util.TechnologyTransformer;

/**
 * Technology entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Entity
@ApiTransformer(TechnologyTransformer.class)
public class Technology extends BaseEntity<String> {

  @Id
  private String id;

  @Index
  private String name;

  @Unindex
  private String shortDescription;

  @Unindex
  private String description;

  @Unindex
  private String website;

  @Unindex
  private String author;

  @Unindex
  private String image;

  /** company recommendation info. */
  @Unindex
  private String recommendation;

  @Index
  private Integer positiveRecomendationsCounter = 0;

  @Index
  private Integer negativeRecomendationsCounter = 0;

  @Index
  private Integer commentariesCounter = 0;

  @Index
  private Integer endorsedsCounter = 0;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(String recommendation) {
    this.recommendation = recommendation;
  }

  public Integer getPositiveRecomendationsCounter() {
    return positiveRecomendationsCounter;
  }

  public void setPositiveRecomendationsCounter(Integer positiveRecomendationsCounter) {
    this.positiveRecomendationsCounter = positiveRecomendationsCounter;
  }

  public Integer getNegativeRecomendationsCounter() {
    return negativeRecomendationsCounter;
  }

  public void setNegativeRecomendationsCounter(Integer negativeRecomendationsCounter) {
    this.negativeRecomendationsCounter = negativeRecomendationsCounter;
  }

  public Integer getCommentariesCounter() {
    return commentariesCounter;
  }

  public void setCommentariesCounter(Integer commentariesCounter) {
    this.commentariesCounter = commentariesCounter;
  }

  public Integer getEndorsedsCounter() {
    return endorsedsCounter;
  }

  public void setEndorsedsCounter(Integer endorsedsCounter) {
    this.endorsedsCounter = endorsedsCounter;
  }

  /**
   * Add 1 to the positive recomndations counter.
   */
  public void addPositiveRecomendationsCounter() {
    this.positiveRecomendationsCounter++;
  }

  /**
   * Remove 1 to the positive recomndations counter.
   */
  public void removePositiveRecomendationsCounter() {
    if (this.positiveRecomendationsCounter > 0) {
      this.positiveRecomendationsCounter--;
    } else {
      this.positiveRecomendationsCounter = 0;
    }
  }

  /**
   * Add 1 to the negative recomndations counter.
   */
  public void addNegativeRecomendationsCounter() {
    this.negativeRecomendationsCounter++;
  }

  /**
   * Remove 1 to the negative recomndations counter.
   */
  public void removeNegativeRecomendationsCounter() {
    if (this.negativeRecomendationsCounter > 0) {
      this.negativeRecomendationsCounter--;
    } else {
      this.negativeRecomendationsCounter = 0;
    }
  }

  /**
   * Add 1 to the commentary counter.
   */
  public void addCommentariesCounter() {
    this.commentariesCounter++;
  }

  /**
   * Remove 1 to the commentary counter.
   */
  public void removeCommentariesCounter() {
    if (this.commentariesCounter > 0) {
      this.commentariesCounter--;
    } else {
      this.commentariesCounter = 0;
    }
  }
}
