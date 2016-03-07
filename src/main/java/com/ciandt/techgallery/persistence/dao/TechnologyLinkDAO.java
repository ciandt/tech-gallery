package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyLink;

import java.util.Date;
import java.util.List;

/**
 * Interface of TechnologyLink
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 *
 */
public interface TechnologyLinkDAO extends GenericDAO<TechnologyLink, Long> {

  /**
   * Method to find all the links of a technology.
   *
   * @param technology to filter the links.
   *
   * @return all links of that technology.
   */
  List<TechnologyLink> findAllByTechnology(Technology technology);

  /**
   * Find all links of a technology starting from date.
   *
   * @param technology.
   * @param start date.
   * @return list of links starting from a specific date.
   */
  List<TechnologyLink> findAllLinksStartingFrom(Technology technology, Date date);
}
