package com.ciandt.techgallery.sample.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.sample.persistence.model.Recommendation;
import com.ciandt.techgallery.sample.persistence.model.Technology;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class TechnologyDAOImpl {

  public List<Technology> findAll() {
    Objectify objectify = OfyService.ofy();
    // field 'name' is indexed.
    List<Technology> techs = objectify.load().type(Technology.class).order("-name").list();
    return techs;
  }

  public List<Technology> findTechnologiesByRecommendation(Recommendation recommendation) {
    Objectify objectify = OfyService.ofy();
    Key<Recommendation> key = Key.create(Recommendation.class, recommendation.getId());

    // field 'name' is indexed.
    List<Technology> techs =
        objectify.load().type(Technology.class).filter("recommendation", key).list();
    return techs;
  }

  public boolean addTechnology(final Technology technology) {
    Objectify objectify = OfyService.ofy();
    objectify.save().entity(technology).now();

    // if group ID != null, it was saved
    if (technology.getId() != null) {
      return true;
    } else {
      return false;
    }

  }

}
