package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.transformer.TechnologyCommentTransformer;
import com.ciandt.techgallery.utils.timezone.TimezoneManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity of Comments
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 * 
 */
@Entity
@ApiTransformer(TechnologyCommentTransformer.class)
public class TechnologyComment extends BaseEntity<Long> {

  public static final String ID = "";
  public static final String COMMENT = "comment";
  public static final String TECHNOLOGY = "technology";
  public static final String AUTHOR = "author";
  public static final String TIMESTAMP = "timestamp";
  public static final String ACTIVE = "active";

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private Long id;

  @Unindex
  private String comment;

  @Index
  @Load
  private Ref<Technology> technology;

  @Unindex
  @Load
  private Ref<TechGalleryUser> author;

  @Index
  private Date timestamp;

  @Index
  private Boolean active;

  /*
   * Constructors -----------------------------------------
   */
  public TechnologyComment() {

  }

  /**
   * Construct for TechnologyComment.
   * @param comment text of the commentary
   * @param technology commented technology
   * @param author author of the commentary
   * @param timestamp when the commentary was done
   * @param active true if the comentary is active 
   */
  public TechnologyComment(String comment, Technology technology, TechGalleryUser author,
      Date timestamp, Boolean active) {
    super();
    setComment(comment);
    setTechnology(Ref.create(technology));
    setAuthor(Ref.create(author));
    setTimestamp(timestamp);
    setActive(active);
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

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Ref<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Ref<Technology> technology) {
    this.technology = technology;
  }

  public Ref<TechGalleryUser> getAuthor() {
    return author;
  }
  
  public TechGalleryUser getAuthorEntity() {
    return author.get();
  }

  public void setAuthor(Ref<TechGalleryUser> author) {
    this.author = author;
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

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
