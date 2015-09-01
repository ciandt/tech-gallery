package com.ciandt.techgallery.sample.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.sample.persistence.model.RecommendationSample;
import com.ciandt.techgallery.sample.persistence.model.TechnologySample;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class TechnologySampleDAOImpl extends GenericSampleObjectifyDAO<TechnologySample, Long>
    implements TechnologySampleDAO {

  public List<TechnologySample> findTechnologiesByRecommendation(RecommendationSample recommendation) {
    Objectify objectify = OfyService.ofy();
    Key<RecommendationSample> key = Key.create(RecommendationSample.class, recommendation.getId());

    // field 'name' is indexed.
    List<TechnologySample> techs =
        objectify.load().type(TechnologySample.class).filter("recommendation", key).list();
    return techs;
  }

}
