package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.util.List;

/**
 * UserDAOImpl methods interface.
 *
 * @author bliberal
 *
 */
public interface TechGalleryUserDAO extends GenericDAO<TechGalleryUser, Long> {

  /**
   * Method that searches user by its email(used to authenticate into the app).
   */
  TechGalleryUser findByLogin(String email);

  /**
   * Method the searches user by its google id provided by UserAPI.
   *
   * @return the user by his google id.
   */
  TechGalleryUser findByGoogleId(String id);

  /**
   * Searches an user by her name and email (AND query).
   *
   * @param name user name
   * @param email user email
   *
   * @return the user by his name and e-mail
   */
  TechGalleryUser findByNameAndEmail(String name, String email);

  /**
   * Searches for an user by email.
   *
   * @param email of the user.
   *
   * @return the user by his e-mail
   */
  TechGalleryUser findByEmail(String email);
  
  /**
   * Searches for a list of users that follow one or more technologies.
   * 
   * @return list of followers.
   */
  List<TechGalleryUser> findAllFollowers();
}
