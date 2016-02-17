package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.TechnologyLinkDAO;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyLink;

import java.util.Date;
import java.util.List;

/**
 * Class that implements DAO of TechnologyLink
 *
 * @author Sidharta Noleto
 * @since 22/09/2015
 *
 */
public class TechnologyLinkDAOImpl extends GenericDAOImpl<TechnologyLink, Long>
    implements TechnologyLinkDAO {

  private static TechnologyLinkDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private TechnologyLinkDAOImpl() {}

  /**
   * Singleton method for the DAO.
   * @return TechnologyLinkDAOImpl instance.
   */
  public static TechnologyLinkDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyLinkDAOImpl();
    }
    return instance;
  }

  @Override
  public List<TechnologyLink> findAllByTechnology(Technology technology) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyLink> entities =
        objectify.load().type(TechnologyLink.class).order("-" + TechnologyLink.TIMESTAMP)
            .filter(TechnologyLink.TECHNOLOGY, Ref.create(technology)).list();

    return entities;
  }

  @Override
  public List<TechnologyLink> findAllLinksStartingFrom(Technology technology, Date date) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyLink> links =
        objectify.load().type(TechnologyLink.class)
            .filter(TechnologyLink.TIMESTAMP + " >", date)
            .filter(TechnologyLink.TECHNOLOGY, technology).list();
    if (links == null || links.size() <= 0) {
      return null;
    }
    return links;
  }
}
