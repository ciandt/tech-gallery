package com.ciandt.techgallery.persistence.model;

import com.ciandt.techgallery.service.transformer.ProjectTransformer;
import com.google.api.server.spi.config.ApiTransformer;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.Date;

/**
 * Project entity.
 *
 * @author moizes
 *
 */
@Entity
@ApiTransformer(ProjectTransformer.class)
public class Project extends BaseEntity<Long> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String NAME = "name";

  @Id
  private Long id;

    @Index
    private String name;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compare the Project entity by ID
     *
     * @param project entity.
     */
    public int compareTo(Project project) {
        return this.id.compareTo(project.getId());
    }
}
