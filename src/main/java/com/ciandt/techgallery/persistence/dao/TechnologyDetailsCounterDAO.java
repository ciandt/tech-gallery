package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.counter.TechnologyDetailsCounter;

/**
 * TechnologyDAOImpl methods interface.
 * 
 * @author ibrahim
 *
 */
public interface TechnologyDetailsCounterDAO extends GenericDAO<TechnologyDetailsCounter, Long> {

  TechnologyDetailsCounter findByTechnology(Technology technology);

}
