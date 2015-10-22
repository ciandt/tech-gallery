package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;

/**
 * TechnologyFollowersDAOImpl methods interface.
 *
 * @author ibrahim
 *
 */
public interface TechnologyFollowersDAO extends GenericDAO<TechnologyFollowers, String> {

  /**
   * Method to find TechnologyFollowers by Technology
   * 
   * @param technology technology info.
   * @return TechnologyFollowers or null;
   */
  public TechnologyFollowers findByTechnology(Technology technology);
}
