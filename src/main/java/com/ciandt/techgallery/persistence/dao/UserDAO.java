package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

/**
 * UserDAOImpl methods interface.
 * 
 * @author bliberal
 *
 */
public interface UserDAO extends GenericDAO<TechGalleryUser, Long> {

  public void updateProfilePicture(TechGalleryUser user, String picture);

  /**
   * Method that searches user by its email(used to authenticate into the app)
   */
  public TechGalleryUser findByLogin(String email);
}
