package com.ciandt.techgallery.persistence.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.counter.TechnologyDetailsCounter;

import java.util.List;

/**
 * TechnologyDAOImpl methods implementation.
 * 
 * @author ibrahim
 *
 */
public class TechnologyDetailsCounterDAOImpl extends GenericDAOImpl<TechnologyDetailsCounter, Long>
    implements TechnologyDetailsCounterDAO {

  private static TechnologyDetailsCounterDAOImpl instance;
  
  /**
   * Singleton TechnologyDetailsCounterDAOImpl.
   */
  public static TechnologyDetailsCounterDAOImpl getInstance() {
    if (instance == null) {
      return new TechnologyDetailsCounterDAOImpl();
    }
    return instance;
  }
  
  @Override
  public TechnologyDetailsCounter findByTechnology(Technology technology) {
    Objectify objectify = OfyService.ofy();
    List<TechnologyDetailsCounter> entityList = objectify.load()
        .type(TechnologyDetailsCounter.class).filter("technology", Ref.create(technology)).list();
    if (entityList != null && !entityList.isEmpty()) {
      return entityList.get(0);
    }
    return null;
  }

}
