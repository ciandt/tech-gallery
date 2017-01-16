package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.ApplicationConfiguration;

/**
 * TechnologyDAOImpl methods interface.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public interface ApplicationConfigurationDAO extends GenericDAO<ApplicationConfiguration, String> {

  /**
   * Find or creates a setting with that id.
   */
  public ApplicationConfiguration findOrCreateById(String id);

}
