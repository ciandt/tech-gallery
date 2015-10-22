package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.TechnologyFollowersDAO;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;

/**
 * TechnologyFollowersDAOImpl methods implementation.
 *
 * @author ibrahim
 *
 */
public class TechnologyFollowersDAOImpl extends GenericDAOImpl<TechnologyFollowers, String>
    implements TechnologyFollowersDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyFollowersDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private TechnologyFollowersDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @return TechnologyDAOImpl instance.
   */
  public static TechnologyFollowersDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyFollowersDAOImpl();
    }
    return instance;
  }

  @Override
  public TechnologyFollowers findByTechnology(Technology technology) {
    final Objectify objectify = OfyService.ofy();
    TechnologyFollowers entity = objectify.load().type(TechnologyFollowers.class)
        .filter(TechnologyFollowers.TECHNOLOGY, technology).first().now();

    return entity;
  }
}
