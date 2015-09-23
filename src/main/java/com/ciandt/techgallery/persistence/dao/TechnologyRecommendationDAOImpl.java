package com.ciandt.techgallery.persistence.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

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

  @Override
  public List<TechnologyRecommendation> findAllActivesByTechnology(Technology technology) {
    Objectify objectify = OfyService.ofy();
    List<TechnologyRecommendation> recommendations =
        objectify.load().type(TechnologyRecommendation.class)
        .filter("technology", Ref.create(technology)).filter("active", Boolean.TRUE).list();

    return recommendations;
  }

}
