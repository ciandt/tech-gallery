package com.ciandt.techgallery.service.model;

/**
 * Response with a technology entity.
 *
 * @author felipers
 *
 */
public class TechnologyTO implements Response {

  /** technology id. */
  private String id;
  /** technology name. */
  private String name;
  /** technology short description. */
  private String shortDescription;
  /** technology description. */
  private String description;
  /** technology website. */
  private String website;
  /** technology author. */
  private String author;
  /** technology image. */
  private String image;
  /** technology company recommendation. */
  private String recommendation;
  /** technology positives recommendations. */
  private Integer positiveRecommendationsCounter;
  /** technology negative recommendations. */
  private Integer negativeRecommendationsCounter;
  /** technology commentaries. */
  private Integer commentariesCounter;
  /** technology endorseds. */
  private Integer endorsersCounter;

  public String getId() {
    return id;
  }

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

  public Integer getPositiveRecommendationsCounter() {
    return positiveRecommendationsCounter;
  }

  public void setPositiveRecommendationsCounter(Integer positiveRecommendationsCounter) {
    this.positiveRecommendationsCounter = positiveRecommendationsCounter;
  }

  public Integer getNegativeRecommendationsCounter() {
    return negativeRecommendationsCounter;
  }

  public void setNegativeRecommendationsCounter(Integer negativeRecommendationsCounter) {
    this.negativeRecommendationsCounter = negativeRecommendationsCounter;
  }

  public Integer getCommentariesCounter() {
    return commentariesCounter;
  }

  public void setCommentariesCounter(Integer commentariesCounter) {
    this.commentariesCounter = commentariesCounter;
  }

  public Integer getEndorsersCounter() {
    return endorsersCounter;
  }

  public void setEndorsersCounter(Integer endorsersCounter) {
    this.endorsersCounter = endorsersCounter;
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
}
