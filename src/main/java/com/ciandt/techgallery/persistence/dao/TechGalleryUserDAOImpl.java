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
public class TechGalleryUserDAOImpl extends GenericDAOImpl<TechGalleryUser, Long> implements
    TechGalleryUserDAO {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public TechGalleryUser findByGoogleId(String id) {
    Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter("googleId", id).first().now();

    return entity;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TechGalleryUser findByEmail(String email) {
    Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter("email", email).first().now();

    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TechGalleryUser findByNameAndEmail(String name, String email) {
    Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity =
        objectify.load().type(TechGalleryUser.class).filter("email", email).filter("name", name)
            .first().now();

    return entity;
  }

}
