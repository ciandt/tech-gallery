package com.ciandt.techgallery.persistence.model.profile;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Key;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.transformer.profile.SubItemCommentTransformer;

import java.util.Date;

@ApiTransformer(SubItemCommentTransformer.class)
public class SubItemComment implements Comparable<SubItemComment> {

  private String originCommentKey;

  private String body;

  private Date timestamp;

  public SubItemComment() {}

  public SubItemComment(String body, Date timestamp) {
    this.body = body;
    this.timestamp = timestamp;
  }

  public Key<TechnologyComment> getOriginComment() {
    return Key.create(originCommentKey);
  }

  public void setOriginComment(Key<TechnologyComment> originComment) {
    this.originCommentKey = originComment.getString();
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

  /**
   * Newer creation dates (timestamp) come first. If equal, follows lexicographical order for the
   * body.
   */
  @Override
  public int compareTo(SubItemComment other) {
    int timeStampComparison = (-1) * this.getTimestamp().compareTo(other.getTimestamp());
    return timeStampComparison != 0 ? timeStampComparison
        : this.getBody().compareTo(other.getBody());
  }


}
