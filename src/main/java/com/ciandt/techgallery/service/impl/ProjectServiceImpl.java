package com.ciandt.techgallery.service.impl;

import com.ciandt.techgallery.persistence.dao.ProjectDAO;
import com.ciandt.techgallery.persistence.dao.SkillDAO;
import com.ciandt.techgallery.persistence.dao.impl.ProjectDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.SkillDAOImpl;
import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.persistence.model.Skill;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.ProjectService;
import com.ciandt.techgallery.service.SkillService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.model.UserSkillTO;
import com.ciandt.techgallery.utils.TechGalleryUtil;
import com.ciandt.techgallery.utils.i18n.I18n;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Services for Skill Endpoint requests.
 *
 * @author Felipe Goncalves de Castro
 */
public class ProjectServiceImpl implements ProjectService {

    /*
     * Constants --------------------------------------------
     */
    private static final Logger log = Logger.getLogger(ProjectServiceImpl.class.getName());
    private static final I18n i18n = I18n.getInstance();

    /*
     * Attributes --------------------------------------------
     */
    private static ProjectServiceImpl instance;
    ProjectDAO projectDao = ProjectDAOImpl.getInstance();

    /**
     * Technology service.
     */
    TechnologyService techService = TechnologyServiceImpl.getInstance();
    /**
     * tech gallery user service.
     */
    UserServiceTG userService = UserServiceTGImpl.getInstance();

    /*
     * Constructors --------------------------------------------
     */
    private ProjectServiceImpl() {
    }

    /**
     * Singleton method for the service.
     *
     * @return SkillServiceImpl instance.
     * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
     * @since 07/10/2015
     */
    public static ProjectServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProjectServiceImpl();
        }
        return instance;
    }

    /*
     * Methods --------------------------------------------
     */
    @Override
    public Project addOrUpdateProject(Project project, User user)
            throws InternalServerErrorException, BadRequestException, NotFoundException {

        log.info("Starting creating or updating skill");

        validateInputs(project, user);

        final Project newProject;

        if (projectDao.findById(project.getId()) != null) {
            projectDao.update(project);
            newProject = project;
        } else {
            newProject = addNewProject(project);
        }

        return newProject;
    }

    @Override
    public void deleteProject(Long projId, User user) throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {

        Project projectEntity = getProject(projId, user);
        projectDao.delete(projectEntity);
    }

    /**
     * Validate inputs of SkillResponse.
     *
     * @param project inputs to be validate
     * @param user    info about user from google
     * @throws BadRequestException          for the validations.
     * @throws InternalServerErrorException in case something goes wrong
     * @throws NotFoundException            in case the information are not founded
     */
    private void validateInputs(Project project, User user)
            throws BadRequestException, NotFoundException, InternalServerErrorException {

        log.info("Validating inputs of skill");

        if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
            throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
        }

        if (project == null || project.getName() == null) {
            throw new BadRequestException(ValidationMessageEnums.NAME_CANNOT_BLANK.message());
        }
    }

    private Project addNewProject(Project project) {
        log.info("Adding new skill...");

        final Project newProject = new Project();
        newProject.setName(project.getName());
        newProject.setId(newProject.getId());

        projectDao.add(newProject);

        log.info("New skill added: " + newProject.getId());

        return newProject;
    }

    @Override
    public Project getProject(Long projId, User user) throws BadRequestException,
            OAuthRequestException, NotFoundException, InternalServerErrorException {
        // user google id
        String googleId;
        // user from techgallery datastore
        TechGalleryUser tgUser;
        // User from endpoint can't be null
        if (user == null) {
            throw new OAuthRequestException(i18n.t("OAuth error, null user reference!"));
        } else {
            googleId = user.getUserId();
        }

        // TechGalleryUser can't be null and must exists on datastore
        if (googleId == null || googleId.equals("")) {
            throw new BadRequestException(i18n.t("Current user was not found!"));
        } else {
            // get the TechGalleryUser from datastore or PEOPLE API
            tgUser = userService.getUserByGoogleId(googleId);
            if (tgUser == null) {
                throw new BadRequestException(i18n.t("Endorser user do not exists on datastore!"));
            }
        }

        final Project project = projectDao.findById(projId);
        if (project == null) {
            throw new NotFoundException(i18n.t("User skill do not exist!"));
        } else {
            return project;
        }
    }

    @Override
    public Project getProject(Long projId, TechGalleryUser user) throws BadRequestException,
            OAuthRequestException, NotFoundException, InternalServerErrorException {
        // User can't be null
        if (user == null) {
            throw new OAuthRequestException(i18n.t("Null user reference!"));
        }

        final Project project = projectDao.findById(projId);
        if (project == null) {
            throw new NotFoundException(i18n.t("User skill do not exist!"));
        } else {
            return project;
        }
    }

    @Override
    public List<Project> getProjects() throws BadRequestException {
        return projectDao.findAll();
    }

}
