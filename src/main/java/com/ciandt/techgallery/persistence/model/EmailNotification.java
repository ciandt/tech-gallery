package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.List;

@Entity
public class EmailNotification extends BaseEntity<Long> {

  @Id
  private Long id;
  @Index
  private String technologyId;
  @Unindex
  private List<String> recipients;
  @Unindex
  private String rule;
  @Unindex
  private String reason;
  @Unindex
  private String emailStatus;
  @Unindex
  private Long timestampSend;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTechnologyId() {
    return technologyId;
  }

  public void setTechnologyId(String technologyId) {
    this.technologyId = technologyId;
  }

  public List<String> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<String> recipients) {
    this.recipients = recipients;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getEmailStatus() {
    return emailStatus;
  }

  public void setEmailStatus(String emailStatus) {
    this.emailStatus = emailStatus;
  }

  public Long getTimestampSend() {
    return timestampSend;
  }

  public void setTimestampSend(Long timestampSend) {
    this.timestampSend = timestampSend;
  }

}
