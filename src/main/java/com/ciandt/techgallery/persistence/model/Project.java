package com.ciandt.techgallery.persistence.model;

import com.ciandt.techgallery.service.transformer.ProjectTransformer;
import com.google.api.server.spi.config.ApiTransformer;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Project entity.
 *
 * @author moizes
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
     * Compare the Project entity by Name
     *
     * @param project entity.
     */
    public int compareTo(Project project){
        int result = Integer.MAX_VALUE;
        if(project != null) {
            result = this.name.compareTo(project.getName());
        }
        return result;
    }
}
