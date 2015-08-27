package com.ciandt.techgallery.sample.service;

import com.ciandt.techgallery.sample.persistence.model.Technology;
import com.ciandt.techgallery.sample.service.model.TechResponse;

/**
 * TechnologyService Interface.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface TechService extends Service<TechResponse, Technology, Long> {

}
