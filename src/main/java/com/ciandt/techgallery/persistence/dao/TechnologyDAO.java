package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;

import java.util.List;

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

  /**
   * Method to get the technology by id active.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 28/10/2015
   *
   * @param id to find the technology.
   *
   * @return the technology if is active.
   */
  public Technology findByIdActive(String id);
  
  /**
   * Method to get the technology by id active.
   *
   * @author <a href="mailto:loraine@ciandt.com"> Loraine Oliveira Duarte </a>
   * @since 05/02/2019
   *
   *
   * @return all the tecnology with no project linked.
   */
  List<Technology> findAllWithNoProjects();
}
