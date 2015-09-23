package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Class of Technology Recommendation
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 23/09/2015
 *
 */
@Entity
public class TechnologyRecommendation extends BaseEntity<Long> {

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private Long id;

  @Unindex
  private Boolean score;

  @Unindex
  @Load
  private TechnologyComment comment;

  @Index
  private Boolean active;

  @Unindex
  @Load
  private TechGalleryUser recommender;

  @Index
  @Load
  private Technology technology;

  /*
   * Constructors -----------------------------------------
   */
  public TechnologyRecommendation() {

  }

  public TechnologyRecommendation(Boolean score, TechnologyComment comment, Boolean active,
      TechGalleryUser recommender, Technology technology) {
    super();
    this.score = score;
    this.comment = comment;
    this.active = active;
    this.recommender = recommender;
    this.technology = technology;
  }

  /*
   * Getter's and Setter's----------------------------------
   */
  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getScore() {
    return score;
  }

  public void setScore(Boolean score) {
    this.score = score;
  }

  public TechnologyComment getComment() {
    return comment;
  }

  public void setComment(TechnologyComment comment) {
    this.comment = comment;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public TechGalleryUser getRecommender() {
    return recommender;
  }

  public void setRecommender(TechGalleryUser recommender) {
    this.recommender = recommender;
  }

  public Technology getTechnology() {
    return technology;
  }

  public void setTechnology(Technology technology) {
    this.technology = technology;
  }
}
