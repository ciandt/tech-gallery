package com.ciandt.techgallery.persistence.model.profile;

import com.google.api.server.spi.config.ApiTransformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import com.ciandt.techgallery.persistence.model.BaseEntity;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.transformer.profile.UserProfileTransformer;

import java.util.HashMap;
import java.util.Map;

@Entity
@ApiTransformer(UserProfileTransformer.class)
public class UserProfile extends BaseEntity<String> {

  public static final int POSITIVE_RECOMMENDATION = 1;

  public static final int NEGATIVE_RECOMMENDATION = -1;

  public static final int OTHER = 0;

  @Id
  private String id;

  @Index
  private Ref<TechGalleryUser> owner;

  private Map<String, UserProfileItem> positiveRecItems = new HashMap<>();

  private Map<String, UserProfileItem> negativeRecItems = new HashMap<>();

  private Map<String, UserProfileItem> otherItems = new HashMap<>();

  public UserProfile() {}

  /**
   * Constructor that receives a profile owner as parameter.
   * 
   * @param owner the TechGalleryUser who owns the profile
   */
  public UserProfile(TechGalleryUser owner) {
    super();
    setOwner(Ref.create(owner));
    setId(getIdFromTgUserId(owner.getId()));
  }

  public static String getIdFromTgUserId(Long tgUserId) {
    // TODO improve this id?
    return "profile" + tgUserId;
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
  public void addItem(int category, Key<Technology> technology, UserProfileItem profileItem) {
    switch (category) {
      case POSITIVE_RECOMMENDATION:
        negativeRecItems.remove(technology.getString());
        otherItems.remove(technology.getString());
        positiveRecItems.put(technology.getString(), profileItem);
        break;
      case NEGATIVE_RECOMMENDATION:
        otherItems.remove(technology.getString());
        positiveRecItems.remove(technology.getString());
        negativeRecItems.put(technology.getString(), profileItem);
        break;
      case OTHER:
        positiveRecItems.remove(technology.getString());
        negativeRecItems.remove(technology.getString());
        otherItems.put(technology.getString(), profileItem);
        break;
      default:
        break;
    }
  }

  /**
   * Searches on every category for the specified technology item
   * 
   * @param technology the technology associated with the item
   * @return the item, if exists. null otherwise
   */
  public UserProfileItem getItem(Key<Technology> technology) {
    UserProfileItem item = positiveRecItems.get(technology.getString());
    if (item == null) {
      item = negativeRecItems.get(technology.getString());
      if (item == null) {
        return otherItems.get(technology.getString());
      }
    }
    return item;
  }

  /**
   * Informs the category of a given technology item.
   * 
   * @param technology the technology associated with the item
   * @return the category where the item is stored
   */
  public Integer getItemCategory(Key<Technology> technology) {
    if (positiveRecItems.containsKey(technology.getString())) {
      return POSITIVE_RECOMMENDATION;
    } else if (negativeRecItems.containsKey(technology.getString())) {
      return NEGATIVE_RECOMMENDATION;
    } else if (otherItems.containsKey(technology.getString())) {
      return OTHER;
    }
    return null;
  }

  /**
   * Removes the item from whichever category it is in.
   * 
   * @param technology the Technology item
   */
  public void removeItem(Key<Technology> technology) {
    positiveRecItems.remove(technology.getString());
    negativeRecItems.remove(technology.getString());
    otherItems.remove(technology.getString());
  }


  public Map<String, UserProfileItem> getPositiveRecItems() {
    return positiveRecItems;
  }


  public Map<String, UserProfileItem> getNegativeRecItems() {
    return negativeRecItems;
  }


  public Map<String, UserProfileItem> getOtherItems() {
    return otherItems;
  }

}
