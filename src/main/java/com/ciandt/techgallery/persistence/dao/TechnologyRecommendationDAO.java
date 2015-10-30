package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

import java.util.Date;
import java.util.List;

/**
 * Interface of TechnologyRecommendation
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 23/09/2015
 *
 */
public interface TechnologyRecommendationDAO extends GenericDAO<TechnologyRecommendation, Long> {

  /**
   * Method to find all the actives Recommendations of the Technology.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 23/09/2015
   *
   * @param technology to filter the informations
   *
   * @return all recommendations actives by technology
   */
  List<TechnologyRecommendation> findAllActivesByTechnology(Technology technology);

  /**
   * Finds the active recommendations by a user in a technology.
   *
   * @param tgUser Recommender
   * @param technology Technology
   * @return a recommendation, if exists
   */
  TechnologyRecommendation findActiveByRecommenderAndTechnology(TechGalleryUser tgUser,
      Technology technology);

  /**
   * Finds a recommendation by a comment.
   *
   * @param comment comment
   */
  TechnologyRecommendation findByComment(TechnologyComment comment);
  

  /**
   * Find all recommendations of a technology starting from date.
   * 
   * @param technology.
   * @param start date.
   * @return list of recommendations starting from a specific date.
   */
  List<TechnologyRecommendation> findAllRecommendationsStartingFrom(Technology technology,
      Date date);
}
