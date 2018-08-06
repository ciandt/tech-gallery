package com.ciandt.techgallery.service.transformer;

import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.service.model.ProjectTO;
import com.google.api.server.spi.config.Transformer;

public class ProjectTransformer implements Transformer<Project, ProjectTO> {

  @Override
  public Project transformFrom(ProjectTO baseObject) {
    Project project = new Project();
    project.setId(baseObject.getId());
    project.setName(baseObject.getName());
    return project;
  }

  @Override
  public ProjectTO transformTo(Project baseObject) {
    if (baseObject.getInactivatedDate() == null) {
      ProjectTO project = new ProjectTO();
      project.setId(baseObject.getId());
      project.setName(baseObject.getName());
      return project;
    } else {
      return null;
    }
  }

}
