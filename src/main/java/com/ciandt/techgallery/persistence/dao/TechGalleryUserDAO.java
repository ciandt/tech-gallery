package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

/**
 * UserDAOImpl methods interface.
 * 
 * @author bliberal
 *
 */
public interface TechGalleryUserDAO extends GenericDAO<TechGalleryUser, Long>{

  public TechGalleryUser findByGplusId(final String gplusId);
}
