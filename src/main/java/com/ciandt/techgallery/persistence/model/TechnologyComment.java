package com.ciandt.techgallery.persistence.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Entity of Comments
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 *
 */
@Entity
public class TechnologyComment extends BaseEntity<Long> {

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private Long id;

  @Unindex
  private String comment;

  @Index
  @Load
  private Technology technology;

  @Unindex
  @Load
  private TechGalleryUser author;

  @Index
  private Date timestamp;

  @Index
  private Boolean active;

  /*
   * Constructors -----------------------------------------
   */
  public TechnologyComment(String comment, Technology technology, TechGalleryUser author,
      Date timestamp, Boolean active) {
    super();
    setComment(comment);
    setTechnology(technology);
    setAuthor(author);
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

  public Technology getTechnology() {
    return technology;
  }

  public void setTechnology(Technology technology) {
    this.technology = technology;
  }

  public TechGalleryUser getAuthor() {
    return author;
  }

  public void setAuthor(TechGalleryUser author) {
    this.author = author;
  }

  public Date getTimestamp() {
    return timestamp;
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
