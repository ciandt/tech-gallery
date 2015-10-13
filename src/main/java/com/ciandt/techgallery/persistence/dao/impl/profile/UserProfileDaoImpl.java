package com.ciandt.techgallery.persistence.dao.impl.profile;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.impl.GenericDAOImpl;
import com.ciandt.techgallery.persistence.dao.profile.UserProfileDao;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;

/**
 * Implementation of UserProfileDao.
 * 
 * @author eduardogf
 *
 */
public class UserProfileDaoImpl extends GenericDAOImpl<UserProfile, String>
    implements UserProfileDao {

  private static UserProfileDaoImpl instance;

  private UserProfileDaoImpl() {}

  /**
   * Singleton.
   *
   * @return UserProfileDaoImpl instance.
   */
  public static UserProfileDaoImpl getInstance() {
    if (instance == null) {
      instance = new UserProfileDaoImpl();
    }
    return instance;
  }

  /**
   * Finds a user profile by its owner's Ref.
   * 
   * @param owner the profile owner
   * @return the UserProfile of the owner
   */
  @Override
  public UserProfile findByUser(Key<TechGalleryUser> owner) {
    Objectify objectify = OfyService.ofy();
    UserProfile entity = objectify.load().type(UserProfile.class)
        .id(UserProfile.getIdFromTgUserId(owner.getId())).now();
    return entity;
  }

}
