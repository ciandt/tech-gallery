package com.ciandt.techgallery.sample.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.sample.persistence.model.Recommendation;
import com.googlecode.objectify.Objectify;

public class RecommendationDAOImpl {
  public List<Recommendation> findAll() {
    Objectify objectify = OfyService.ofy();
    // field 'name' is indexed.
    List<Recommendation> recs = objectify.load().type(Recommendation.class).order("-score").list();
    return recs;
  }

  public boolean addRecommendation(final Recommendation recommendation) {
    Objectify objectify = OfyService.ofy();
    objectify.save().entity(recommendation).now();

    // if group ID != null, it was saved
    if (recommendation.getId() != null) {
      return true;
    } else {
      return false;
    }

  }
}
