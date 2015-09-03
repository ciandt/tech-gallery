package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.googlecode.objectify.Objectify;

/**
 * UserDAOImpl methods implementation.
 * 
 * @author bliberal
 *
 */
public class UserDAOImpl extends GenericDAOImpl<TechGalleryUser, Long> implements UserDAO {

  @Override
  public void updateProfilePicture(TechGalleryUser user, String picture) {
    user.setEmail(picture);
    update(user);
  }

   
  /**
   * {@inheritDoc}
   */
  @Override
  public TechGalleryUser findByLogin(String email) {
    Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter("email", email).first().now();

    return entity;
  }
}
