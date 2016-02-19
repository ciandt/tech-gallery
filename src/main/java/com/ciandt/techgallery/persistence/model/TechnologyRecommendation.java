package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.transformer.TechnologyRecommendationTransformer;
import com.ciandt.techgallery.utils.timezone.TimezoneManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class of Technology Recommendation
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 23/09/2015
 *
 */
@Entity
@ApiTransformer(TechnologyRecommendationTransformer.class)
public class TechnologyRecommendation extends BaseEntity<Long> {

  public static final String ID = "id";
  public static final String SCORE = "score";
  public static final String COMMENT = "comment";
  public static final String ACTIVE = "active";
  public static final String RECOMMENDER = "recommender";
  public static final String TECHNOLOGY = "technology";
  public static final String TIMESTAMP = "timestamp";

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private Long id;

  @Unindex
  private Boolean score;

  @Index
  @Load
  private Ref<TechnologyComment> comment;

  @Index
  private Boolean active;

  @Index
  @Load
  private Ref<TechGalleryUser> recommender;

  @Index
  @Load
  private Ref<Technology> technology;
  
  @Index
  private Date timestamp;

  /*
   * Constructors -----------------------------------------
   */
  public TechnologyRecommendation() {

  }

  /**
   * Construct for TechnologyRecommendation.
   * 
   * @param score recommendation score: true = positive, false = negative
   * @param comment the comment associated with the recommendation
   * @param active whether the recommendation is active or not
   * @param recommender the user who recommended the technology
   * @param technology the technology recommended
   */
  public TechnologyRecommendation(Boolean score, TechnologyComment comment, Boolean active,
      TechGalleryUser recommender, Technology technology) {
    super();
    this.score = score;
    this.comment = Ref.create(comment);
    this.active = active;
    this.recommender = Ref.create(recommender);
    this.technology = Ref.create(technology);
  }

  /*
   * Getter's and Setter's----------------------------------
   */
  @Override
  public Long getId() {
    return id;
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

  public Ref<TechnologyComment> getComment() {
    return comment;
  }
  
  public TechnologyComment getCommentEntity() {
    return comment.get();
  }

  public void setComment(Ref<TechnologyComment> comment) {
    this.comment = comment;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Ref<TechGalleryUser> getRecommender() {
    return recommender;
  }

  public void setRecommender(Ref<TechGalleryUser> recommender) {
    this.recommender = recommender;
  }
  
  public TechGalleryUser getRecommenderEntity() {
    return recommender.get();
  }
  
  public Ref<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Ref<Technology> technology) {
    this.technology = technology;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getFormattedTimestamp() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss");
    return formatter.format(TimezoneManager.getInstance().convertToUserTimezone(this.timestamp));
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  public String getScoreImg() {
    return this.score ? Constants.THUMBS_UP : Constants.THUMBS_DOWN;
  }
  
}
