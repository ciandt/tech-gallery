package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;

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
  public TechnologyFollowers getTechnologyFollowersByTechnology(Technology technology)
      throws BadRequestException;

  /**
   * Service for updating technologyFollowers.
   * 
   * @param technologyFollowers new values of the entity.
   * @throws BadRequestException in case a request with problem were made. 
   */
  public void update(TechnologyFollowers technologyFollowers) throws BadRequestException;

  /**
   * Service for delete technologyFollowers.
   * 
   * @param technologyFollowers to be deleted.
   * @throws BadRequestException in case a request with problem were made. 
   */
  public void delete(TechnologyFollowers technologyFollowers);
}
