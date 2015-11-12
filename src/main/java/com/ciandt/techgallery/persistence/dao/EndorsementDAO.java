package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.Date;
import java.util.List;

/**
 * EndorsementDAOImpl methods interface.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public interface EndorsementDAO extends GenericDAO<Endorsement, Long> {

  /**
   * Method that return a list of endorsements by technology.
   *
   * @param techId to filter all endorsements by this Id.
   *
   * @return list of endorsements based on technology
   */
  List<Endorsement> findAllByTechnology(String techId);

  /**
   * Method that return a list of active endorsements by technology.
   *
   * @param techId to filter all endorsements actives by this Id.
   * @return list of active endorsements based on technology
   */
  List<Endorsement> findAllActivesByTechnology(String techId);

  /**
   * Method that return an endorsement by its endorser, endorsed and technology.
   *
   * @param endorser the endorsement.
   * @param endorsed who was endorsement.
   * @param technology that has the endorsement.
   *
   * @return all endorsements by users and technology.
   */
  List<Endorsement> findByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology);

  /**
   * Method that return an active endorsement by its endorser, endorsed and technology.
   *
   * @param endorser the endorsement.
   * @param endorsed who was endorsement.
   * @param technology that has the endorsement.
   *
   * @return all endorsements actives by users and technology.
   */
  List<Endorsement> findActivesByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology);
  
  List<Endorsement> findAllEndorsementsStartingFrom(TechGalleryUser userEndorsed, Date date);

}
