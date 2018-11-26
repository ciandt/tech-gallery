package com.ciandt.techgallery.service.endpoint;

import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.service.ProjectService;
import com.ciandt.techgallery.service.impl.ProjectServiceImpl;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import java.util.List;

/**
 * Endpoint controller class for Project requests.
 *
 * @author Felipe Goncalves de Castro
 *
 */

@ApiReference(TechGalleryApiDefinition.class)
public class ProjectEndpoint {

    private ProjectService service = ProjectServiceImpl.getInstance();

    /**
     * Endpoint for adding a Project.
     *
     * @param json with project info.
     * @param user oauth user.
     * @return added project
     * @throws InternalServerErrorException in case something goes wrong
     * @throws NotFoundException in case the information are not founded
     * @throws BadRequestException in case a request with problem were made.
     */
    @ApiMethod(name = "addProject", path = "project", httpMethod = "post")
    public Project addProject(Project project, User user)
            throws InternalServerErrorException, BadRequestException, NotFoundException {
        return service.addOrUpdateProject(project, user);
    }

    @ApiMethod(name = "deleteProject", path = "project", httpMethod = "delete")
    public void deleteProject(@Named("id") Long projId, User user)
            throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
        service.deleteProject(projId, user);
    }

    /**
     * Endpoint for getting an User Project.
     *
     * @param id technology id.
     * @param user oauth user.
     * @return the user project
     * @throws InternalServerErrorException when an unknown error occurs
     * @throws BadRequestException when some request parameter is wrong missing
     * @throws OAuthRequestException when the user is not valid
     * @throws NotFoundException when the user project is not found
     */
    @ApiMethod(name = "getProject", path = "project", httpMethod = "get")
    public Project getProject(@Named("id") Long id, User user) throws InternalServerErrorException,
            BadRequestException, OAuthRequestException, NotFoundException {
        return service.getProject(id, user);
    }

    /**
     * Endpoint for getting all Projects.
     *
     * @return all projects
     * @throws BadRequestException when some request parameter is wrong missing
     */
    @ApiMethod(name = "getAllProjects", path = "projects", httpMethod = "get")
    public List<Project> getProjects() throws BadRequestException{
        return service.getProjects();
    }

}
