package com.ciandt.techgallery.service.impl.profile;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

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
import com.ciandt.techgallery.service.impl.TechnologyServiceImpl;
import com.ciandt.techgallery.service.impl.UserServiceTGImpl;
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


  private UserProfileServiceImpl() {}

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
    return item.getSkillLevel() > 0 || !item.getComments().isEmpty()
        || item.getEndorsementQuantity() > 0;
  }

  @Override
  public UserProfile findUserProfileByEmail(String email) throws NotFoundException {
    TechGalleryUser owner = UserServiceTGImpl.getInstance().getUserByEmail(email);
    return findUserProfileByOwner(owner);
  }

  @Override
  public UserProfile addItem(Technology technology, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    TechGalleryUser owner = UserServiceTGImpl.getInstance().getUserByEmail(user.getEmail());
    UserProfile profile = findUserProfileByOwner(owner);
    technology = TechnologyServiceImpl.getInstance().getTechnologyById(technology.getId(), user);
    UserProfileItem newItem = new UserProfileItem(technology);
    profile.addItem(UserProfile.POSITIVE_RECOMMENDATION, Key.create(technology), newItem);
    profile.addItem(UserProfile.NEGATIVE_RECOMMENDATION, Key.create(technology), newItem);
    profile.addItem(UserProfile.OTHER, Key.create(technology), newItem);
    UserProfileDaoImpl.getInstance().add(profile);
    return profile;
  }

  // TODO cache profile
  @Override
  public UserProfile createProfile(TechGalleryUser tgUser) {
    UserProfile profile = findUserProfileByOwner(tgUser);
    if (profile == null) {
      profile = new UserProfile(tgUser);
      UserProfileDaoImpl.getInstance().add(profile);
    }
    return profile;
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
      UserProfileDaoImpl.getInstance().add(profile);
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
        } else {
          profile.addItem(category, technologyKey, item);
        }
      } else {

        // Comment deletion case
        item.removeComment(comment);
        if (category == UserProfile.OTHER) {
          if (!itemHasOtherPropertiesSet(item)) {
            profile.removeItem(technologyKey);
          }
        }
      }
      UserProfileDaoImpl.getInstance().add(profile);
    }
  }

  /**
   * When a user sets a new skill level, this method is called to propagate such change on the
   * user's profile.
   * 
   * @param skill the changed skill
   */
  @Override
  public void handleSkillChanges(Skill skill) {
    Ref<Technology> technologyRef = skill.getTechnology();
    Technology technology = technologyRef.get();
    Key<Technology> technologyKey = technologyRef.getKey();
    UserProfile profile = findUserProfileByOwner(skill.getTechGalleryUser().get());

    if (profile != null) {
      UserProfileItem item = getTechnologyItem(technology, profile);
      Integer category = profile.getItemCategory(technologyKey);

      if (skill.getValue() != null && skill.getValue() > 0) {
        item.setSkillLevel(skill.getValue());
        // No previous category means new item
        if (category == null) {
          profile.addItem(UserProfile.OTHER, technologyKey, item);
        } else {
          profile.addItem(category, technologyKey, item);
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
      UserProfileDaoImpl.getInstance().add(profile);
    }
  }

  /**
   * When a user endorses another user, this method is called to propagate such change on the
   * endorsed user profile.
   * 
   * @param endorsement the recently changed endorsement
   */
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
        } else {
          profile.addItem(category, technologyKey, item);
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
      UserProfileDaoImpl.getInstance().add(profile);
    }
  }



}
