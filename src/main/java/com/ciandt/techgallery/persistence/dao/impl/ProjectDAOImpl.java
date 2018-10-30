package com.ciandt.techgallery.persistence.dao.impl;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.ProjectDAO;
import com.ciandt.techgallery.persistence.model.Project;
import com.googlecode.objectify.Objectify;

/**
 * TechnologyDAOImpl methods implementation.
 *
 * @author Felipe Goncalves de Castro
 *
 */
public class ProjectDAOImpl extends GenericDAOImpl<Project, Long> implements ProjectDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static ProjectDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private ProjectDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return TechnologyDAOImpl instance.
   */
  public static ProjectDAOImpl getInstance() {
    if (instance == null) {
      instance = new ProjectDAOImpl();
    }
    return instance;
  }

  @Override
  public Project findByName(String name) {
    final Objectify objectify = OfyService.ofy();
    Project entity =
        objectify.load().type(Project.class).filter(Project.NAME, name).first().now();

    return entity;
  }

  @Override
  public Project findById(Long id) {
    return super.findById(id);
  }
}
