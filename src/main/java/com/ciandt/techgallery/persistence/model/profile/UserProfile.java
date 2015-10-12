package com.ciandt.techgallery.persistence.model.profile;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import com.ciandt.techgallery.persistence.model.BaseEntity;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.transformer.profile.UserProfileTransformer;

import java.util.HashMap;

@Entity
@ApiTransformer(UserProfileTransformer.class)
public class UserProfile extends BaseEntity<String> {

  static final int POSITIVE_RECOMMENDATION = 1;

  static final int NEGATIVE_RECOMMENDATION = -1;

  static final int OTHER_RECOMMENDATION = 0;

  @Id
  private String id;

  @Index
  private Ref<TechGalleryUser> owner;

  private HashMap<Ref<Technology>, UserProfileItem> positiveRecItems;

  private HashMap<Ref<Technology>, UserProfileItem> negativeRecItems;

  private HashMap<Ref<Technology>, UserProfileItem> otherItems;


  /**
   * Constructor that receives a profile owner as parameter.
   * 
   * @param owner the TechGalleryUser who owns the profile
   */
  public UserProfile(Ref<TechGalleryUser> owner) {
    super();
    setOwner(owner);
    // TODO improve this id
    setId("profile" + owner.getKey().getId());
    positiveRecItems = new HashMap<Ref<Technology>, UserProfileItem>();
    negativeRecItems = new HashMap<Ref<Technology>, UserProfileItem>();
    otherItems = new HashMap<Ref<Technology>, UserProfileItem>();
  }


  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public Ref<TechGalleryUser> getOwner() {
    return owner;
  }

  public void setOwner(Ref<TechGalleryUser> owner) {
    this.owner = owner;
  }

  /**
   * Adds a profile item into a category. Removes the item from any other category it might be
   * inserted.
   * 
   * @param category the category to insert the item into
   * @param technology the Technology associated to the item
   * @param profileItem the profile item itself
   */
  public void addItem(int category, Ref<Technology> technology, UserProfileItem profileItem) {
    if (category == POSITIVE_RECOMMENDATION) {
      negativeRecItems.remove(technology);
      otherItems.remove(technology);
      positiveRecItems.put(technology, profileItem);
    } else if (category == NEGATIVE_RECOMMENDATION) {
      otherItems.remove(technology);
      positiveRecItems.remove(technology);
      negativeRecItems.put(technology, profileItem);
    } else if (category == OTHER_RECOMMENDATION) {
      positiveRecItems.remove(technology);
      negativeRecItems.remove(technology);
      otherItems.put(technology, profileItem);
    }
  }

  /**
   * Removes the item from whichever category it is in.
   * 
   * @param technology the Technology item
   */
  public void removeItem(Ref<Technology> technology) {
    positiveRecItems.remove(technology);
    negativeRecItems.remove(technology);
    otherItems.remove(technology);
  }


  public HashMap<Ref<Technology>, UserProfileItem> getPositiveRecItems() {
    return positiveRecItems;
  }


  public HashMap<Ref<Technology>, UserProfileItem> getNegativeRecItems() {
    return negativeRecItems;
  }


  public HashMap<Ref<Technology>, UserProfileItem> getOtherItems() {
    return otherItems;
  }

}
