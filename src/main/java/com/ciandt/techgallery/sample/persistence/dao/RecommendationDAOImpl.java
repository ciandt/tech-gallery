package com.ciandt.techgallery.sample.persistence.dao;

import com.ciandt.techgallery.sample.persistence.model.Recommendation;

public class RecommendationDAOImpl extends GenericObjectFyDAO<Recommendation, Long> implements
    RecommendationDAO {

  /*
   * public List<Recommendation> findAll() { Objectify objectify = OfyService.ofy(); // field 'name'
   * is indexed. List<Recommendation> recs =
   * objectify.load().type(Recommendation.class).order("-score").list(); return recs; }
   */

  /*
   * public boolean addRecommendation(final Recommendation recommendation) { Objectify objectify =
   * OfyService.ofy(); objectify.save().entity(recommendation).now();
   * 
   * // if group ID != null, it was saved if (recommendation.getId() != null) { return true; } else
   * { return false; }
   * 
   * }
   */
}
