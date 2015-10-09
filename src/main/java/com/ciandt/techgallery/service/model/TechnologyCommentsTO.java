package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechnologyComment;

import java.util.List;

/**
 * TechnologyCommentTO.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentsTO implements Response {

  List<TechnologyComment> comments;

  public List<TechnologyComment> getComments() {
    return comments;
  }

  public void setComments(List<TechnologyComment> comments) {
    this.comments = comments;
  }

}
