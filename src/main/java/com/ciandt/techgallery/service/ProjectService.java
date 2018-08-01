package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import java.util.List;

/**
 * Services for Skills.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface ProjectService {

  /**
   * Service for adding a technology.
   * 
   * @param project json with skill info.
   * @return project info or message error.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  Project addOrUpdateProject(Project project, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException;

  void deleteProject(Long projId, User user)
          throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException;

  /**
   * Service for getting an user skill.
   * 
   * @param projId project id.
   * @param user oauth user.
   * @return project info or message error.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  Project getProject(Long projId, User user) throws BadRequestException, OAuthRequestException,
      NotFoundException, InternalServerErrorException;

  /**
   * Service for getting a TechGalleyUser skill.
   * 
   * @param projId project id.
   * @param user datastore user.
   * @return project info or message error.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  Project getProject(Long projId, TechGalleryUser user) throws BadRequestException,
      OAuthRequestException, NotFoundException, InternalServerErrorException;

  /**
   * Service for getting all projects.
   *
   * @return List of all projects.
   * @throws BadRequestException in case a request with problem were made.
   */
  List<Project> getProjects() throws BadRequestException;
}
