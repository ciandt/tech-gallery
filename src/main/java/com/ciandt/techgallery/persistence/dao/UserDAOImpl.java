package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

/**
 * UserDAOImpl methods implementation.
 * 
 * @author bliberal
 *
 */
public class UserDAOImpl extends GenericDAOImpl<TechGalleryUser, Long>implements UserDAO {

  public void updateProfilePicture(TechGalleryUser user, String picture) {
    user.setEmail(picture);
    update(user);
  }
}
