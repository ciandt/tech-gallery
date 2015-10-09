package com.ciandt.techgallery.service.model;

/**
 * Response with a technology entity.
 * 
 * @author felipers
 *
 */
public class TechnologyResponse implements Response {

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
  private Integer positiveRecomendationsCounter;
  /** technology negative recommendations. */
  private Integer negativeRecomendationsCounter;
  /** technology commentaries. */
  private Integer commentariesCounter;
  /** technology endorseds. */
  private Integer endorsedsCounter;

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
