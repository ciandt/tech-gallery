package com.ciandt.techgallery.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

/**
 * EndorsementDAOImpl methods interface.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface EndorsementDAO extends GenericDAO<Endorsement, Long> {

  /**
   * Method that return a list of endorsements by technology
   * 
   * @param technology
   * @return list of endorsements based on technology
   */
  public List<Endorsement> findAllByTechnology(String techId);

  /**
   * Method that return a list of active endorsements by technology
   * 
   * @param technology
   * @return list of active endorsements based on technology
   */
  public List<Endorsement> findAllActivesByTechnology(String techId);

  /**
   * Method that return an endorsement by its users and technology
   * 
   * @param endorser
   * @param endorsed
   * @param technology
   * @return
   */
  public List<Endorsement> findByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology);

  /**
   * Method that return an active endorsement by its users and technology
   * 
   * @param endorser
   * @param endorsed
   * @param technology
   * @return
   */
  public List<Endorsement> findActivesByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology);

}
