package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;

import com.ciandt.techgallery.ofy.OfyService;
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

  @Override
  public Technology findByName(String name) {
    final Objectify objectify = OfyService.ofy();
    Technology entity =
        objectify.load().type(Technology.class).filter(Technology.NAME, name).first().now();

    return entity;
  }

  @Override
  public Technology findByIdActive(String id) {
    Technology technology = super.findById(id);
    if (technology != null && technology.getActive()) {
      return technology;
    }
    return null;
  }
}
