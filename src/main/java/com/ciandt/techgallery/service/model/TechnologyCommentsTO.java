package com.ciandt.techgallery.service.model;

import java.util.List;

/**
 * TechnologyCommentTO.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentsTO implements Response {

  List<TechnologyCommentTO> comments;

  public List<TechnologyCommentTO> getComments() {
    return comments;
  }

  public void setComments(List<TechnologyCommentTO> comments) {
    this.comments = comments;
  }

}
