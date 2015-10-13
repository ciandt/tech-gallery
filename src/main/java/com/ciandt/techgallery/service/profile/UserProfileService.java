package com.ciandt.techgallery.service.profile;

import com.google.api.server.spi.response.NotFoundException;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
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

  //TODO comment this method
  void handleSkillChanges(Skill skill);

  //TODO comment this method
  void handleEndorsement(Endorsement endorsement);

  UserProfile findUserProfileByEmail(String email) throws NotFoundException;

  UserProfile createProfile(TechGalleryUser tgUser);

}
