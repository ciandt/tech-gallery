package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

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
   * @return List<TechnologyRecommendation>
   */
  public List<TechnologyRecommendation> findAllActivesByTechnology(Technology technology);
}
