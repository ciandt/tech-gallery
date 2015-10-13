package com.ciandt.techgallery.service.impl.profile;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.impl.profile.UserProfileDaoImpl;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.ciandt.techgallery.service.profile.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {
  
  private static UserProfileServiceImpl instance;
  
  static final int RECOMMEND_TECHNOLOGY_POSITIVELY = 1;
  static final int RECOMMEND_TECHNOLOGY_NEGATIVELY = 2;
  static final int REMOVE_TECHNOLOGY_RECOMMENDATION = 3;
  static final int MAKE_COMMENT = 4;
  static final int REMOVE_COMMENT = 5;
  static final int SET_SKILL = 6;
  static final int ENDORSE = 7;
  static final int REMOVE_ENDORSEMENT = 8;


  private UserProfileServiceImpl(){}
  /**
   * Singleton method for the service.
   *
   * @return UserProfileServiceImpl instance.
   */
  public static UserProfileServiceImpl getInstance() {
    if (instance == null) {
      instance = new UserProfileServiceImpl();
    }
    return instance;
  }

  private UserProfile findUserProfileByOwner(TechGalleryUser user) {
    return UserProfileDaoImpl.getInstance().findByUser(Key.create(user));
  }

  private UserProfileItem getTechnologyItem(Technology technology, UserProfile profile) {
    // If the user already has an item for this technology, find it.
    UserProfileItem item = profile.getItem(Key.create(technology));
    // Create a new one if it doesn't exist
    if (item == null) {
      item = new UserProfileItem(technology);
    }
    return item;
  }

  private Boolean itemHasOtherPropertiesSet(UserProfileItem item) {
    return item.getSkillLevel() > 0 && !item.getComments().isEmpty()
        && item.getEndorsementQuantity() > 0;
  }

  /**
   * Given a recommendation (active or inactive, positive or negative) places the Technology item in
   * the category according to the recommendation score and status.
   * 
   * @param recommendation the reference to the recommendation to be handled
   */
  @Override
  public void handleRecommendationChanges(TechnologyRecommendation recommendation) {
    Ref<Technology> technologyRef = recommendation.getTechnology();
    Technology technology = technologyRef.get();
    Key<Technology> technologyKey = technologyRef.getKey();
    UserProfile profile = findUserProfileByOwner(recommendation.getRecommender().get());

    if (profile != null) {
      UserProfileItem item = getTechnologyItem(technology, profile);
      // If recommendation is active, add to positive/negative categories according to
      // recommendation score
      if (recommendation.getActive()) {
        if (recommendation.getScore()) {
          profile.addItem(UserProfile.POSITIVE_RECOMMENDATION, technologyKey, item);
        } else {
          profile.addItem(UserProfile.NEGATIVE_RECOMMENDATION, technologyKey, item);
        }
        // If recommendation is not active, move to category "other" only if item has other
        // properties set
      } else {
        if (itemHasOtherPropertiesSet(item)) {
          profile.addItem(UserProfile.OTHER, technologyKey, item);
        } else {
          profile.removeItem(technologyKey);
        }
      }
    }
  }

  /**
   * Given a comment (active or inactive), applies the following rules:<br>
   * If it is an active comment:<br>
   * If it's the first user activity on the technology, creates a new item on "other". Otherwise,
   * just add the comment on the item.<br>
   * If it is an inactive comment:<br>
   * Removes the comment from the item If the item is on "other" and is the last activity (no more
   * comments, skill or endorsements for the user), removes the whole item.
   * 
   * @param comment the comment that was added or removed
   */
  @Override
  public void handleCommentChanges(TechnologyComment comment) {
    Ref<Technology> technologyRef = comment.getTechnology();
    Technology technology = technologyRef.get();
    Key<Technology> technologyKey = technologyRef.getKey();
    UserProfile profile = findUserProfileByOwner(comment.getAuthor().get());

    if (profile != null) {
      UserProfileItem item = getTechnologyItem(technology, profile);
      Integer category = profile.getItemCategory(technologyKey);

      // Adds a comment to the item and inserts the item into "other" if it's a new item
      if (comment.getActive()) {
        item.addComment(comment);
        // No previous category means new item
        if (category == null) {
          profile.addItem(UserProfile.OTHER, technologyKey, item);
        }
      } else {

        // Comment deletion case
        item.removeComment(Key.create(comment));
        if (category == UserProfile.OTHER) {
          if (!itemHasOtherPropertiesSet(item)) {
            profile.removeItem(technologyKey);
          }
        }
      }
    }
  }

  // TODO comment this method
  @Override
  public void handleSkillChanges(Skill skill) {
    Ref<Technology> technologyRef = skill.getTechnology();
    Technology technology = technologyRef.get();
    Key<Technology> technologyKey = technologyRef.getKey();
    UserProfile profile = findUserProfileByOwner(skill.getTechGalleryUser().get());

    if (profile != null) {
      UserProfileItem item = getTechnologyItem(technology, profile);
      Integer category = profile.getItemCategory(technologyKey);

      if (skill.getActive()) {
        item.setSkillLevel(skill.getValue());
        // No previous category means new item
        if (category == null) {
          profile.addItem(UserProfile.OTHER, technologyKey, item);
        }
      } else {

        // Skill deletion case
        item.setSkillLevel(0);
        if (category == UserProfile.OTHER) {
          if (!itemHasOtherPropertiesSet(item)) {
            profile.removeItem(technologyKey);
          }
        }
      }
    }
  }

  // TODO comment this method
  @Override
  public void handleEndorsement(Endorsement endorsement) {
    Ref<Technology> technologyRef = endorsement.getTechnology();
    Technology technology = technologyRef.get();
    Key<Technology> technologyKey = technologyRef.getKey();
    UserProfile profile = findUserProfileByOwner(endorsement.getEndorsed().get());

    if (profile != null) {
      UserProfileItem item = getTechnologyItem(technology, profile);
      Integer category = profile.getItemCategory(technologyKey);

      if (endorsement.isActive()) {
        item.addToEndorsementsCounter(1);
        // No previous category means new item
        if (category == null) {
          profile.addItem(UserProfile.OTHER, technologyKey, item);
        }
      } else {

        // Endorsement decrement case
        item.addToEndorsementsCounter(-1);
        if (category == UserProfile.OTHER) {
          if (!itemHasOtherPropertiesSet(item)) {
            profile.removeItem(technologyKey);
          }
        }
      }
    }
  }



}
