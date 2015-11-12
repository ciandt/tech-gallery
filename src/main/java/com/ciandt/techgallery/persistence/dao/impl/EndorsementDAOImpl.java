package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.EndorsementDAO;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EndorsementDAOImpl methods implementation.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public class EndorsementDAOImpl extends GenericDAOImpl<Endorsement, Long>
    implements EndorsementDAO {

  private static EndorsementDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private EndorsementDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return EndorsementDAOImpl instance.
   */
  public static EndorsementDAOImpl getInstance() {
    if (instance == null) {
      instance = new EndorsementDAOImpl();
    }
    return instance;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Endorsement> findAllByTechnology(String techId) {
    final Technology tech = new Technology();
    tech.setId(techId);
    final Objectify objectify = OfyService.ofy();
    final List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(tech)).list();

    if (entities == null || entities.size() <= 0) {
      return new ArrayList<Endorsement>();
    }
    return entities;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Endorsement> findAllActivesByTechnology(String techId) {
    final Technology tech = new Technology();
    tech.setId(techId);
    final Objectify objectify = OfyService.ofy();
    final List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(tech)).filter("active", true).list();

    if (entities == null || entities.size() <= 0) {
      return new ArrayList<Endorsement>();
    }
    return entities;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Endorsement> findByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology) {
    final Objectify objectify = OfyService.ofy();
    final List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(technology))
        .filter("endorser", Ref.create(endorser)).filter(Endorsement.ENDORSED, Ref.create(endorsed))
        .list();

    return entities;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Endorsement> findActivesByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology) {
    final Objectify objectify = OfyService.ofy();
    final List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(technology))
        .filter("endorser", Ref.create(endorser)).filter(Endorsement.ENDORSED, Ref.create(endorsed))
        .filter("active", true).list();

    return entities;
  }
  
  @Override
  public List<Endorsement> findAllEndorsementsStartingFrom(TechGalleryUser userEndorsed, Date date) {
    final Objectify objectify = OfyService.ofy();
    final List<Endorsement> endorsements =
        objectify.load().type(Endorsement.class)
            .filter(Endorsement.TIMESTAMP + " >", date)
            .filter(Endorsement.ENDORSED, Ref.create(userEndorsed))
            .filter(Endorsement.ACTIVE, Boolean.TRUE).list();
    if (endorsements == null || endorsements.size() <= 0) {
      return null;
    }
    return endorsements;
  }
}
