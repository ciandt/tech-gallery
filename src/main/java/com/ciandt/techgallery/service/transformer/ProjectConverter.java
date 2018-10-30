package com.ciandt.techgallery.service.transformer;

import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.ProjectResponse;
import com.ciandt.techgallery.service.model.SkillResponse;
import com.google.api.server.spi.config.Transformer;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * SkillConverter methods.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class ProjectConverter implements Transformer<Project, ProjectResponse> {

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  @Override
  public ProjectResponse transformTo(Project entity) {
    ProjectResponse projectResponse = new ProjectResponse();

    projectResponse.setId(entity.getId());
    projectResponse.setName(entity.getName());

    return projectResponse;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param transient entity
   * @return entity from datastore
   */
  @Override
  public Project transformFrom(ProjectResponse arg0) {
    Project product = new Project();

    product.setId(arg0.getId());
    product.setName(arg0.getName());

    return product;
  }
}
