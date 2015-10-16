package com.ciandt.techgallery.service.transformer.profile;

import com.google.api.server.spi.config.Transformer;

import com.ciandt.techgallery.persistence.model.profile.SubItemComment;
import com.ciandt.techgallery.service.model.profile.SubItemCommentTo;

public class SubItemCommentTransformer implements Transformer<SubItemComment, SubItemCommentTo> {

  /**
   * Comments in a user profile are not supposed to be entered from outside the system.
   */
  @Override
  public SubItemComment transformFrom(SubItemCommentTo arg0) {
    return null;
  }

  @Override
  public SubItemCommentTo transformTo(SubItemComment arg0) {
    return new SubItemCommentTo(arg0.getBody(), arg0.getTimestamp());
  }

}
