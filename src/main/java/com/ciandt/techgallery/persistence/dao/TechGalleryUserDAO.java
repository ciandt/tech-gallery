package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

/**
 * UserDAOImpl methods interface.
 * 
 * @author bliberal
 *
 */
public interface TechGalleryUserDAO extends GenericDAO<TechGalleryUser, Long> {

  /**
   * Method that searches user by its email(used to authenticate into the app)
   */
  public TechGalleryUser findByLogin(String email);

  /**
   * Method the searches user by its google id provided by UserAPI
   * 
   * @return
   */
  public TechGalleryUser findByGoogleId(String id);

  /**
   * Searches an user by her name and email (AND query)
   * 
   * @param name user name
   * @param email user email
   * @return
   */
  public TechGalleryUser findByNameAndEmail(String name, String email);

  /**
   * Searches for an user by email
   * 
   * @param email
   * @return
   */
  public TechGalleryUser findByEmail(String email);
}
