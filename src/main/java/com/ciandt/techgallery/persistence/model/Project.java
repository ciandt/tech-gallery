package com.ciandt.techgallery.persistence.model;

import com.ciandt.techgallery.service.enums.TechnologyOrderOptionEnum;
import com.ciandt.techgallery.service.transformer.ProjectTransformer;
import com.google.api.server.spi.config.ApiTransformer;
import com.googlecode.objectify.annotation.*;

import java.util.List;

/**
 * Project entity.
 *
 * @author Moizes Clodoaldo Papa Filho
 *
 */
@Entity
@ApiTransformer(ProjectTransformer.class)
public class Project extends BaseEntity<String> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String NAME = "name";

  /*
   * Attributes --------------------------------------------
   */
  @Id
  private String id;

  @Index
  private String name;

  /*
   * Getter's and Setter's --------------------------------------------
   */
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  /**
   * Compare Projects by ID.
   *
   * @param project Project to be compared.
   * @return result of comparison.
   */
  public int compareTo(Project project){
      return this.id.compareTo(project.id);
  }

}
