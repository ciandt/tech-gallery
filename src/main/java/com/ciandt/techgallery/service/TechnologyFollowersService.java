package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;

/**
 * Services for Technologies.
 *
 * @author ibrahim
 *
 */
public interface TechnologyFollowersService {

  /**
   * Service for get a technologyFolloewr by technology.
   *
   * @param technology technology info.
   *
   * @return technologyFollower info or message error.
   * @throws BadRequestException in case a request with problem were made.
   */
  TechnologyFollowers getTechnologyFollowersByTechnology(Technology technology)
      throws BadRequestException;

  /**
   * Service for follow or unfollow technology.
   * 
   * @param technologyId technology Id.
   * @return FollowTechnology entity
   * @throws BadRequestException in case a request with problem were made.
   * @throws NotFoundException in case the information are not founded.
   * @throws InternalServerErrorException in case something goes wrong.
   */
  Technology followTechnology(String technologyId, TechGalleryUser techUser)
      throws BadRequestException, NotFoundException, InternalServerErrorException;

  /**
   * Service for updating technologyFollowers.
   * 
   * @param technologyFollowers new values of the entity.
   * @throws BadRequestException in case a request with problem were made.
   */
  void update(TechnologyFollowers technologyFollowers) throws BadRequestException;

  /**
   * Service for follow a technology.
   * 
   * @param technologyFollowers entity with the technology and the list of followers.
   * @param techUser user who is going to follow.
   * @param technology followed technology.
   * @return entity.
   */
  TechnologyFollowers follow(TechnologyFollowers technologyFollowers, TechGalleryUser techUser,
      Technology technology);

  /**
   * Service for getting a technologyFollowers bys it´s Id.
   * 
   * @param id Id for search the entity.
   * @return TechnologyFollowers entity
   */
  TechnologyFollowers findById(String id);
}
