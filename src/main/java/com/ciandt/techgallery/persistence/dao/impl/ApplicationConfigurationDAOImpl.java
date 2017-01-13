package com.ciandt.techgallery.persistence.dao.impl;

import com.ciandt.techgallery.persistence.dao.ApplicationConfigurationDAO;
import com.ciandt.techgallery.persistence.model.ApplicationConfiguration;

/**
 * ApplicationConfigurationDAODAOImpl methods implementation.
 *
 * @author Marcos Fernandes
 *
 */
public class ApplicationConfigurationDAOImpl extends GenericDAOImpl<ApplicationConfiguration, String> implements ApplicationConfigurationDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static ApplicationConfigurationDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private ApplicationConfigurationDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author Marcos Fernandes
   *
   * @return ApplicationConfigurationDAOImpl instance.
   */
  public static ApplicationConfigurationDAOImpl getInstance() {
    if (instance == null) {
      instance = new ApplicationConfigurationDAOImpl();
    }
    return instance;
  }

  @Override
  /**
   * Find or creates a setting with that id.
   */
  public ApplicationConfiguration findOrCreateById(String id) {
    ApplicationConfiguration item = super.findById(id);
    if(item == null) {
      item = new ApplicationConfiguration();
      item.setId(id);
      item.setValue(null);
      super.add(item);
    }
    return item;
  }

}
