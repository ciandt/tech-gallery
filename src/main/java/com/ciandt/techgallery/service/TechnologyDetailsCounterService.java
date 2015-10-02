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
  public TechnologyDetailsCounter getTechnologyDetailByTechnology(Technology technology)
      throws NotFoundException;

  public void addCommentariesCounter(Technology technology);

  public void removeCommentariesCounter(Technology technology);

  public void addRecomendationCounter(Technology technology, Boolean score);

  public void removeRecomendationCounter(Technology technology, Boolean score);

  public void updateEdorsedsCounter(Technology technology, Integer size);

}
