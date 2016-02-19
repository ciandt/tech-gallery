package com.ciandt.techgallery.service.model.profile;

import java.util.Date;

public class SubItemCommentTo {

  private String body;

  private Date timestamp;

  public SubItemCommentTo() {}

  /**
   * Constructor with parameters.
   * @param body the comment`s body
   * @param timestamp when the comment was created
   */
  public SubItemCommentTo(String body, Date timestamp) {
    super();
    this.body = body;
    this.timestamp = timestamp;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
