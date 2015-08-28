package com.ciandt.techgallery.sample.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.sample.persistence.model.Recommendation;
import com.ciandt.techgallery.sample.persistence.model.Technology;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class TechnologyDAOImpl extends GenericSampleObjectifyDAO<Technology, Long> implements
    TechnologyDAO {

  public List<Technology> findTechnologiesByRecommendation(Recommendation recommendation) {
    Objectify objectify = OfyService.ofy();
    Key<Recommendation> key = Key.create(Recommendation.class, recommendation.getId());

    // field 'name' is indexed.
    List<Technology> techs =
        objectify.load().type(Technology.class).filter("recommendation", key).list();
    return techs;
  }

}
