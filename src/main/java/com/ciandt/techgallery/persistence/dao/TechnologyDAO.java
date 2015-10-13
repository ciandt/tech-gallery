package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;

/**
 * TechnologyDAOImpl methods interface.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public interface TechnologyDAO extends GenericDAO<Technology, String> {

  /**
   * Method to find technologies by name.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param name of the technology to find.
   *
   * @return the technology founded.
   */
  public Technology findByName(String name);
}
