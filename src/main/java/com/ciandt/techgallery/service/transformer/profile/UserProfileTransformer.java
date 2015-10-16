package com.ciandt.techgallery.service.transformer.profile;

import com.google.api.server.spi.config.Transformer;

import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.ciandt.techgallery.service.model.profile.UserProfileTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserProfileTransformer implements Transformer<UserProfile, UserProfileTo> {

  @Override
  public UserProfile transformFrom(UserProfileTo arg0) {
    return null;
  }

  @Override
  public UserProfileTo transformTo(UserProfile arg0) {

    List<UserProfileItem> positiveRecItems = getSortedItems(arg0.getPositiveRecItems());
    List<UserProfileItem> negativeRecItems = getSortedItems(arg0.getNegativeRecItems());
    List<UserProfileItem> otherItems = getSortedItems(arg0.getOtherItems());

    return new UserProfileTo(arg0.getOwner().get(), positiveRecItems, negativeRecItems, otherItems);
  }

  private List<UserProfileItem> getSortedItems(Map<String, UserProfileItem> items) {
    ArrayList<UserProfileItem> sortedItems = new ArrayList<UserProfileItem>(items.values());
    Collections.sort(sortedItems);
    return sortedItems;
  }

}
