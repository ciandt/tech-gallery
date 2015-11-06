package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.util.List;

/**
 * UserDAOImpl methods implementation.
 *
 * @author bliberal
 *
 */
public class TechGalleryUserDAOImpl extends GenericDAOImpl<TechGalleryUser, Long>
    implements TechGalleryUserDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static TechGalleryUserDAOImpl instance;

  /*
   * Constructors --------------------------------------------
   */
  private TechGalleryUserDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return TechGalleryUserDAOImpl instance.
   */
  public static TechGalleryUserDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechGalleryUserDAOImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  /**
   * {@inheritDoc}.
   */
  @Override
  public TechGalleryUser findByLogin(String email) {
    final Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter(TechGalleryUser.EMAIL, email)
        .first().now();

    return entity;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public TechGalleryUser findByGoogleId(String id) {
    final Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter(TechGalleryUser.GOOGLE_ID, id)
        .first().now();

    return entity;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public TechGalleryUser findByEmail(String email) {
    final Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter(TechGalleryUser.EMAIL, email)
        .first().now();

    return entity;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public TechGalleryUser findByNameAndEmail(String name, String email) {
    final Objectify objectify = OfyService.ofy();
    TechGalleryUser entity = null;
    entity = objectify.load().type(TechGalleryUser.class).filter(TechGalleryUser.EMAIL, email)
        .filter(TechGalleryUser.NAME, name).first().now();

    return entity;
  }
  
  /**
   * {@inheritDoc}.
   */
  @Override
  public List<TechGalleryUser> findAllFollowers() {
    final Objectify objectify = OfyService.ofy();
    List<TechGalleryUser> followers =
        objectify.load().type(TechGalleryUser.class)
            .filter(TechGalleryUser.FOLLOWED_TECHNOLOGY_IDS + " !=", null).list();
    return followers;
  }

}