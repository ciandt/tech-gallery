package com.ciandt.techgallery.persistence.model;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Entity of Comments
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22 sept. of 2015
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
  private Ref<Technology> technologies;

  @Unindex
  @Load
  private Ref<TechGalleryUser> techGalleryUsers;

  @Index
  private Date creation;

  @Index
  private Boolean active;

  /*
   * Constructors ----------------------------------
   */
  public TechnologyComment(String comment, Ref<Technology> technologies,
      Ref<TechGalleryUser> techGalleryUsers, Date creation, Boolean active) {
    super();
    setComment(comment);
    setTechnologies(technologies);
    setTechGalleryUsers(techGalleryUsers);
    setCreation(creation);
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

  public Ref<Technology> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(Ref<Technology> technologies) {
    this.technologies = technologies;
  }

  public Ref<TechGalleryUser> getTechGalleryUsers() {
    return techGalleryUsers;
  }

  public void setTechGalleryUsers(Ref<TechGalleryUser> techGalleryUsers) {
    this.techGalleryUsers = techGalleryUsers;
  }

  public Date getCreation() {
    return creation;
  }

  public void setCreation(Date creation) {
    this.creation = creation;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
