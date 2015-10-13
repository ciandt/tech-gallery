package com.ciandt.techgallery.persistence.dao.profile;

import com.googlecode.objectify.Key;

import com.ciandt.techgallery.persistence.dao.GenericDAO;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;

/**
 * UserProfileDao interface.
 * 
 * @author eduardogf
 *
 */
public interface UserProfileDao extends GenericDAO<UserProfile, String> {

  UserProfile findByUser(Key<TechGalleryUser> owner);

}
