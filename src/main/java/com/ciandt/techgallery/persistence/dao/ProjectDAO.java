package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.persistence.model.Technology;

/**
 * TechnologyDAOImpl methods interface.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public interface ProjectDAO extends GenericDAO<Project, Long> {

  /**
   * Method to find technologies by name.
   *
   * @param name of the technology to find.
   *
   * @return the technology founded.
   */
  public Project findByName(String name);

  /**
   * Method to get the technology by id active.
   *
   * @param id to find the technology.
   *
   * @return the technology.
   */
  public Project findById(Long id);
}
