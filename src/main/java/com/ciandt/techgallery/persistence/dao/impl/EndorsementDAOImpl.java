package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.EndorsementDAO;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.ArrayList;
import java.util.List;

/**
 * EndorsementDAOImpl methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class EndorsementDAOImpl extends GenericDAOImpl<Endorsement, Long>implements EndorsementDAO {

  private static EndorsementDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private EndorsementDAOImpl() {}

  public static EndorsementDAOImpl getInstance() {
    if (instance == null) {
      instance = new EndorsementDAOImpl();
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Endorsement> findAllByTechnology(String techId) {
    Technology tech = new Technology();
    tech.setId(techId);
    Objectify objectify = OfyService.ofy();
    List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(tech)).list();

    if (entities == null || entities.size() <= 0) {
      return new ArrayList<Endorsement>();
    }
    return entities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Endorsement> findAllActivesByTechnology(String techId) {
    Technology tech = new Technology();
    tech.setId(techId);
    Objectify objectify = OfyService.ofy();
    List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(tech)).filter("active", true).list();

    if (entities == null || entities.size() <= 0) {
      return new ArrayList<Endorsement>();
    }
    return entities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Endorsement> findByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology) {
    Objectify objectify = OfyService.ofy();
    List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(technology))
        .filter("endorser", Ref.create(endorser)).filter(Endorsement.ENDORSED, Ref.create(endorsed))
        .list();

    return entities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Endorsement> findActivesByUsers(TechGalleryUser endorser, TechGalleryUser endorsed,
      Technology technology) {
    Objectify objectify = OfyService.ofy();
    List<Endorsement> entities = objectify.load().type(Endorsement.class)
        .filter(Endorsement.TECHNOLOGY, Ref.create(technology))
        .filter("endorser", Ref.create(endorser)).filter(Endorsement.ENDORSED, Ref.create(endorsed))
        .filter("active", true).list();

    return entities;
  }
}