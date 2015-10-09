package com.ciandt.techgallery.persistence.dao.impl;

import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.model.Technology;

/**
 * TechnologyDAOImpl methods implementation.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public class TechnologyDAOImpl extends GenericDAOImpl<Technology, String> implements TechnologyDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private TechnologyDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return TechnologyDAOImpl instance.
   */
  public static TechnologyDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyDAOImpl();
    }
    return instance;
  }
}
