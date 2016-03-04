package com.ciandt.techgallery.persistence.model;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.transformer.TechnologyLinkTransformer;
import com.ciandt.techgallery.utils.timezone.TimezoneManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity of Links
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 *
 */
@Entity
@ApiTransformer(TechnologyLinkTransformer.class)
public class TechnologyLink extends BaseEntity<Long> {

  public static final String ID = "";
  public static final String DESCRIPTION = "description";
  public static final String LINK = "link";
  public static final String TECHNOLOGY = "technology";
  public static final String AUTHOR = "author";
  public static final String TIMESTAMP = "timestamp";

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private Long id;

  @Unindex
  private String description;

  @Unindex
  private String link;

  @Index
  @Load
  private Ref<Technology> technology;

  @Unindex
  @Load
  private Ref<TechGalleryUser> author;

  @Index
  private Date timestamp;

  /*
   * Constructors -----------------------------------------
   */
  public TechnologyLink() {

  }

  /**
   */
  public TechnologyLink(String description, String link, Technology technology, TechGalleryUser author,
      Date timestamp) {
    super();
    setDescription(description);
    setLink(link);
    setTechnology(Ref.create(technology));
    setAuthor(Ref.create(author));
    setTimestamp(timestamp);
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
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

}
