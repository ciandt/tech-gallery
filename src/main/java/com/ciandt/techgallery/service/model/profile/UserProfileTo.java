package com.ciandt.techgallery.service.model.profile;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;

import java.util.List;

public class UserProfileTo {

  private TechGalleryUser owner;

  private List<UserProfileItem> positiveRecItems;

  private List<UserProfileItem> negativeRecItems;

  private List<UserProfileItem> otherItems;

  /**
   * All args constructor.
   * 
   * @param owner the profile owner
   * @param positiveRecItems itens with positive recommendation by the owner
   * @param negativeRecItems itens with negative recommendation by the owner
   * @param otherItems itens which the owner interacted with, but did not recommended
   */
  public UserProfileTo(TechGalleryUser owner, List<UserProfileItem> positiveRecItems,
      List<UserProfileItem> negativeRecItems, List<UserProfileItem> otherItems) {
    super();
    this.owner = owner;
    this.positiveRecItems = positiveRecItems;
    this.negativeRecItems = negativeRecItems;
    this.otherItems = otherItems;
  }

  public TechGalleryUser getOwner() {
    return owner;
  }

  public void setOwner(TechGalleryUser owner) {
    this.owner = owner;
  }

  public List<UserProfileItem> getPositiveRecItems() {
    return positiveRecItems;
  }

  public void setPositiveRecItems(List<UserProfileItem> positiveRecItems) {
    this.positiveRecItems = positiveRecItems;
  }

  public List<UserProfileItem> getNegativeRecItems() {
    return negativeRecItems;
  }

  public void setNegativeRecItems(List<UserProfileItem> negativeRecItems) {
    this.negativeRecItems = negativeRecItems;
  }

  public List<UserProfileItem> getOtherItems() {
    return otherItems;
  }

  public void setOtherItems(List<UserProfileItem> otherItems) {
    this.otherItems = otherItems;
  }


}
