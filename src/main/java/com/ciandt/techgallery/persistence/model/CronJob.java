package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import com.ciandt.techgallery.service.enums.CronStatus;

import java.util.Date;

@Entity
public class CronJob extends BaseEntity<Long> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String START_TIMESTAMP = "startTimestamp";
  public static final String END_TIMESTAMP = "endTimestamp";
  public static final String CRON_STATUS = "cronStatus";
  public static final String DESCRIPTION = "description";

  @Id
  Long id;
  
  @Index
  private String name;

  @Index
  private Date startTimestamp;
  
  @Unindex
  private Date endTimestamp;
  
  @Index
  private CronStatus cronStatus;
  
  @Unindex
  private String description;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(Date startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public Date getEndTimestamp() {
    return endTimestamp;
  }

  public void setEndTimestamp(Date endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  public CronStatus getCronStatus() {
    return cronStatus;
  }

  public void setCronStatus(CronStatus cronStatus) {
    this.cronStatus = cronStatus;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
