package com.ciandt.techgallery.persistence.dao.impl;

import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.model.Technology;

/**
 * TechnologyDAOImpl methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class TechnologyDAOImpl extends GenericDAOImpl<Technology, String>implements TechnologyDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private TechnologyDAOImpl() {}

  public static TechnologyDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyDAOImpl();
    }
    return instance;
  }
}
