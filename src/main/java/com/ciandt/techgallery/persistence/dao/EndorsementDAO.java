package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.List;

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
  List<Endorsement> findAllByTechnology(String techId);

  /**
   * Method that return a list of active endorsements by technology
   * 
   * @param technology
   * @return list of active endorsements based on technology
   */
  List<Endorsement> findAllActivesByTechnology(String techId);

  /**
   * Method that return an endorsement by its users and technology
   * 
   * @param endorser
   * @param endorsed
   * @param technology
   * @return
   */
  List<Endorsement> findByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology);

  /**
   * Method that return an active endorsement by its users and technology
   * 
   * @param endorser
   * @param endorsed
   * @param technology
   * @return
   */
  List<Endorsement> findActivesByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology);

}
