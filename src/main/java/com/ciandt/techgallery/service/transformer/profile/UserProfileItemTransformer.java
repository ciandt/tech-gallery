package com.ciandt.techgallery.service.transformer.profile;

import com.google.api.server.spi.config.Transformer;

import com.ciandt.techgallery.persistence.model.profile.SubItemComment;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.ciandt.techgallery.service.model.profile.UserProfileItemTo;

import java.util.ArrayList;
import java.util.Collections;

public class UserProfileItemTransformer implements Transformer<UserProfileItem, UserProfileItemTo> {

  @Override
  public UserProfileItem transformFrom(UserProfileItemTo arg0) {
    return null;
  }

  @Override
  public UserProfileItemTo transformTo(UserProfileItem arg0) {
    ArrayList<SubItemComment> sortedComments =
        new ArrayList<SubItemComment>(arg0.getComments().values());
    Collections.sort(sortedComments);

    return new UserProfileItemTo(arg0.getTechnologyName(),
        arg0.getCompanyRecommendation().message(), arg0.getEndorsementQuantity(),
        arg0.getSkillLevel(), sortedComments);
  }

}
