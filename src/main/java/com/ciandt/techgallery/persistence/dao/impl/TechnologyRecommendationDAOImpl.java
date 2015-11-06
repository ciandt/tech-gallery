package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

import java.util.Date;
import java.util.List;

/**
 * Class that implements DAO of TechnologyRecommendation
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 23/09/2015
 *
 */
public class TechnologyRecommendationDAOImpl extends GenericDAOImpl<TechnologyRecommendation, Long>
    implements TechnologyRecommendationDAO {

  private static TechnologyRecommendationDAOImpl instance;

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyRecommendationDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return TechnologyRecommendationDAOImpl instance.
   */
  public static TechnologyRecommendationDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyRecommendationDAOImpl();
    }
    return instance;
  }

  @Override
  public List<TechnologyRecommendation> findAllActivesByTechnology(Technology technology) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyRecommendation> recommendations =
        objectify.load().type(TechnologyRecommendation.class)
            .filter(TechnologyRecommendation.TECHNOLOGY, Ref.create(technology))
            .filter(TechnologyRecommendation.ACTIVE, Boolean.TRUE).list();

    return recommendations;
  }

  @Override
  public TechnologyRecommendation findActiveByRecommenderAndTechnology(TechGalleryUser tgUser,
      Technology technology) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyRecommendation> recommendations =
        objectify.load().type(TechnologyRecommendation.class)
            .filter(TechnologyRecommendation.TECHNOLOGY, Ref.create(technology))
            .filter(TechnologyRecommendation.ACTIVE, Boolean.TRUE)
            .filter(TechnologyRecommendation.RECOMMENDER, Ref.create(tgUser)).list();
    if (recommendations == null || recommendations.isEmpty()) {
      return null;
    } else {
      return recommendations.get(0);
    }
  }

  @Override
  public TechnologyRecommendation findByComment(TechnologyComment comment) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyRecommendation> recommendations =
        objectify.load().type(TechnologyRecommendation.class)
            .filter(TechnologyRecommendation.COMMENT, Ref.create(comment)).list();
    if (recommendations == null || recommendations.isEmpty()) {
      return null;
    } else {
      return recommendations.get(0);
    }
  }

  @Override
  public List<TechnologyRecommendation> findAllRecommendationsStartingFrom(Technology technology,
      Date date) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyRecommendation> recommendations =
        objectify.load().type(TechnologyRecommendation.class)
            .filter(TechnologyRecommendation.TIMESTAMP + " >", date)
            .filter(TechnologyRecommendation.TECHNOLOGY, technology)
            .filter(TechnologyRecommendation.ACTIVE, Boolean.TRUE).list();
    if (recommendations == null || recommendations.size() <= 0) {
      return null;
    }
    return recommendations;
  }

}
