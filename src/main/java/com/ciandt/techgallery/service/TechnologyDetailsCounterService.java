package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.NotFoundException;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.counter.TechnologyDetailsCounter;

/**
 * Services for Technologies.
 * 
 * @author ibrahim
 *
 */
public interface TechnologyDetailsCounterService {

  /**
   * Service for getting a technology details response.
   * 
   * @param entity technology.
   * @return .
   * @throws NotFoundException .
   */
  TechnologyDetailsCounter getTechnologyDetailByTechnology(Technology technology)
      throws NotFoundException;

  void addCommentariesCounter(Technology technology);

  void removeCommentariesCounter(Technology technology);

  void addRecomendationCounter(Technology technology, Boolean score);

  void removeRecomendationCounter(Technology technology, Boolean score);

  void updateEdorsedsCounter(Technology technology, Integer size);

}
