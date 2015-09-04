package com.ciandt.techgallery.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.persistence.model.Endorsement;
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
  public List<Endorsement> findAllByTechnology(Technology technology);

}
