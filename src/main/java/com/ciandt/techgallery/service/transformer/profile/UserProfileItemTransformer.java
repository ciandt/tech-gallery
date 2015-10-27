package com.ciandt.techgallery.service.transformer.profile;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.ciandt.techgallery.service.model.profile.SubItemCommentTo;
import com.ciandt.techgallery.service.model.profile.UserProfileItemTo;
import com.ciandt.techgallery.utils.Dereferencer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserProfileItemTransformer implements Transformer<UserProfileItem, UserProfileItemTo> {

  @Override
  public UserProfileItem transformFrom(UserProfileItemTo arg0) {
    return null;
  }

  @Override
  public UserProfileItemTo transformTo(UserProfileItem arg0) {
    List<TechnologyComment> comments = getDereferencedComments(arg0);
    List<SubItemCommentTo> sortedCommentsTo = techCommentToSubItemCommentToList(comments);
    sortCommentsByTimestamp(sortedCommentsTo);

    return new UserProfileItemTo(arg0.getTechnologyName(), arg0.getCompanyRecommendation(),
        arg0.getTechnologyPhotoUrl(), arg0.getEndorsementQuantity(), arg0.getSkillLevel(),
        sortedCommentsTo);
  }

  private List<TechnologyComment> getDereferencedComments(UserProfileItem arg0) {
    List<Ref<TechnologyComment>> commentsRefList = new ArrayList<Ref<TechnologyComment>>();
    commentsRefList.addAll(arg0.getComments());
    List<TechnologyComment> sortedComments = Dereferencer.deref(commentsRefList);
    return sortedComments;
  }

  private List<SubItemCommentTo> techCommentToSubItemCommentToList(
      List<TechnologyComment> comments) {
    List<SubItemCommentTo> subItemTos = new ArrayList<SubItemCommentTo>();
    for (TechnologyComment comment : comments) {
      subItemTos.add(techCommentToSubItemComment(comment));
    }
    return subItemTos;

  }

  private SubItemCommentTo techCommentToSubItemComment(TechnologyComment comment) {
    return new SubItemCommentTo(comment.getComment(), comment.getTimestamp());
  }

  private void sortCommentsByTimestamp(List<SubItemCommentTo> commentsTo) {
    Collections.sort(commentsTo, new Comparator<SubItemCommentTo>() {
      @Override
      public int compare(SubItemCommentTo comment1, SubItemCommentTo comment2) {
        return Long.compare(comment2.getTimestamp().getTime(), comment1.getTimestamp().getTime());
      }
    });
  }

}
