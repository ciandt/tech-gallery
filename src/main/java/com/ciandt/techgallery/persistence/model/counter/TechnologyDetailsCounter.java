package com.ciandt.techgallery.persistence.model.counter;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.persistence.model.BaseEntity;
import com.ciandt.techgallery.persistence.model.Technology;

public class TechnologyDetailsCounter extends BaseEntity<Long> {

  @Id
  private Long id;

  @Unindex
  @Load
  private Ref<Technology> technology;

  @Index
  private Integer positiveRecomendationsCounter;

  @Index
  private Integer negativeRecomendationsCounter;

  @Index
  private Integer commentariesCounter;

  @Index
  private Integer endorsementsCounter;


  public Ref<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Ref<Technology> technology) {
    this.technology = technology;
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

  public Integer getEndorsementsCounter() {
    return endorsementsCounter;
  }

  public void setEndorsementsCounter(Integer endorsementsCounter) {
    this.endorsementsCounter = endorsementsCounter;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

}
