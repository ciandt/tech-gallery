package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.NotFoundException;

import com.ciandt.techgallery.persistence.dao.TechnologyDetailsCounterDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDetailsCounterDAOImpl;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.counter.TechnologyDetailsCounter;
import com.ciandt.techgallery.service.TechnologyDetailsCounterService;

/**
 * Services for TechnologyDetailsCounter Endpoint requests.
 * 
 * @author ibrahim
 *
 */
public class TechnologyDetailsCounterServiceImpl implements TechnologyDetailsCounterService {

  private static TechnologyDetailsCounterServiceImpl instance;
  TechnologyDetailsCounterDAO technologyDetailsCounterDao =
      TechnologyDetailsCounterDAOImpl.getInstance();

  /**
   * Singleton TechnologyDetailsCounterServiceImpl.
   */
  public static TechnologyDetailsCounterServiceImpl getInstance() {
    if (instance == null) {
      return new TechnologyDetailsCounterServiceImpl();
    }
    return instance;
  }

  @Override
  public TechnologyDetailsCounter getTechnologyDetailByTechnology(Technology technology)
      throws NotFoundException {
    TechnologyDetailsCounter entity = technologyDetailsCounterDao.findByTechnology(technology);
    return entity;
  }

  @Override
  public void addCommentariesCounter(Technology technology) {
    TechnologyDetailsCounter entity = technologyDetailsCounterDao.findByTechnology(technology);
    if (entity != null) {
      entity.addCommentariesCounter();
    }
    technologyDetailsCounterDao.update(entity);
  }

  @Override
  public void removeCommentariesCounter(Technology technology) {
    TechnologyDetailsCounter entity = technologyDetailsCounterDao.findByTechnology(technology);
    if (entity != null) {
      entity.removeCommentariesCounter();
    }
    technologyDetailsCounterDao.update(entity);

  }

  @Override
  public void addRecomendationCounter(Technology technology, Boolean score) {
    TechnologyDetailsCounter entity = technologyDetailsCounterDao.findByTechnology(technology);
    if (entity == null) {
      return;
    }
    if (score) {
      entity.addPositiveRecomendationsCounter();
    } else {
      entity.addNegativeRecomendationsCounter();
    }
    technologyDetailsCounterDao.update(entity);
  }

  @Override
  public void removeRecomendationCounter(Technology technology, Boolean score) {
    TechnologyDetailsCounter entity = technologyDetailsCounterDao.findByTechnology(technology);
    if (entity == null) {
      return;
    }
    if (score) {
      entity.removePositiveRecomendationsCounter();
    } else {
      entity.removeNegativeRecomendationsCounter();
    }
    technologyDetailsCounterDao.update(entity);
  }

  @Override
  public void updateEdorsedsCounter(Technology technology, Integer size) {
    TechnologyDetailsCounter entity = technologyDetailsCounterDao.findByTechnology(technology);
    if (entity == null) {
      return;
    }
    entity.setEndorsedsCounter(size);
    technologyDetailsCounterDao.update(entity);
  }
}
