package com.ciandt.techgallery.persistence.model.counter;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import com.ciandt.techgallery.persistence.model.BaseEntity;
import com.ciandt.techgallery.persistence.model.Technology;

/**
 * Entity for TechnologyDetailsCounter.
 * 
 * @author ibrahim
 *
 */
@Entity
public class TechnologyDetailsCounter extends BaseEntity<Long> {

  public static final String ID = "ID";
  public static final String TECHNOLOGY = "technology";
  public static final String POSITIVE_RECOMENDATIONS_COUNTER = "positiveRecomendationsCounter";
  public static final String NEGATIVE_RECOMENDATIONS_COUNTER = "negativeRecomendationsCounter";
  public static final String COMMENTARIES_COUNTER = "commentariesCounter";
  public static final String ENDORSEDS_COUNTER = "endorsedsCounter";

  @Id
  private Long id;

  @Index
  @Load
  private Ref<Technology> technology;

  @Index
  private Integer positiveRecomendationsCounter = 0;

  @Index
  private Integer negativeRecomendationsCounter = 0;

  @Index
  private Integer commentariesCounter = 0;

  @Index
  private Integer endorsedsCounter = 0;

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

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Integer getEndorsedsCounter() {
    return endorsedsCounter;
  }

  public void setEndorsedsCounter(Integer endorsedsCounter) {
    this.endorsedsCounter = endorsedsCounter;
  }

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
}
