package com.ciandt.techgallery.service.profile;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;

public interface UserProfileService {
  
  /**
   * Given a recommendation (active or inactive, positive or negative) places the Technology item in
   * the category according to the recommendation score and status.
   * 
   * @param recommendation the reference to the recommendation to be handled
   */
  public void handleRecommendationChanges(TechnologyRecommendation recommendation);

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
  void handleCommentChanges(TechnologyComment comment);

  /**
   * Receives a Skill entity and reflects its state on the user profile.
   * @param skill The recently changed skill
   */
  void handleSkillChanges(Skill skill);

  /**
   * Receives an Endorsement entity and reflects its state on the user profile.
   * @param endorsement the recently changed Endorsement entity
   */
  void handleEndorsement(Endorsement endorsement);

  UserProfile findUserProfileByEmail(String email) throws NotFoundException;

  UserProfile createProfile(TechGalleryUser tgUser);

  UserProfile addItem(Technology technology, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

}
