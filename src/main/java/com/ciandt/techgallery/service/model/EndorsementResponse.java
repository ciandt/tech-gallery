package com.ciandt.techgallery.service.model;

import java.util.Date;

/**
 * Response used by frontend to make an Endorsement.
 * 
 * @author felipers
 *
 */
public class EndorsementResponse implements Response {

  /** endorsement id. */
  private Long id;

  /** TechGalleryUser id. */
  private Long endorser;

  /** endorsed email. */
  private String endorsed;

  private Date timestamp;

  /** technology id or name?. */
  private String technology;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getEndorser() {
    return endorser;
  }

  public void setEndorser(Long endorser) {
    this.endorser = endorser;
  }

  public String getEndorsed() {
    return endorsed;
  }

  public void setEndorsed(String endorsed) {
    this.endorsed = endorsed;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getTechnology() {
    return technology;
  }

  public void setTechnology(String technology) {
    this.technology = technology;
  }

}
