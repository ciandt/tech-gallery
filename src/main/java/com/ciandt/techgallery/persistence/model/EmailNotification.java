package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.Date;
import java.util.List;

@Entity
public class EmailNotification extends BaseEntity<Long> {

  @Id
  private Long id;

  @Unindex
  private List<String> recipients;

  @Unindex
  private String subject;

  @Unindex
  private String body;

  @Unindex
  private String reason;

  @Unindex
  private String emailStatus;

  @Index
  private Date timestamp;

  @Index
  private Date timestampSend;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<String> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<String> recipients) {
    this.recipients = recipients;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
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

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Date getTimestampSend() {
    return timestampSend;
  }

  public void setTimestampSend(Date date) {
    this.timestampSend = date;
  }

}
