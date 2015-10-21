package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Services for Technologies.
 *
 * @author felipers
 *
 */
public interface TechnologyService {

  /**
   * Service for adding a technology.
   *
   * @param technology json with technology info.
   * @param user that is adding a technology.
   *
   * @return technology info or message error.
   *
   * @throws InternalServerErrorException in case some internal server error occur.
   * @throws BadRequestException in case a request with problem were made.
   */
  public Technology addTechnology(Technology technology, User user)
      throws BadRequestException, IOException, GeneralSecurityException;

  /**
   * Service for getting all technologies.
   *
   * @return technologies info or message error.
   * @throws NotFoundException
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  Response getTechnologies() throws InternalServerErrorException, NotFoundException;

  /**
   * Service for getting a technology response.
   *
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  Technology getTechnology(final String id) throws NotFoundException;

  /**
   * Service for getting all technologies according a filter.
   *
   * @param filter entity filter.
   * @return technologies info or message error.
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  Response findTechnologiesByFilter(final TechnologyFilter techFilter, User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException;

  /**
   * Service for getting a technology
   *
   * @param id entity id
   * @return .
   * @throws NotFoundException when entity is not found
   */
  Technology getTechnologyById(String id) throws NotFoundException;

  List<String> getOrderOptions(User user);

  void addCommentariesCounter(Technology technology);

  void removeCommentariesCounter(Technology technology);

  void addRecomendationCounter(Technology technology, Boolean score);

  void removeRecomendationCounter(Technology technology, Boolean score);

  void updateEdorsedsCounter(Technology technology, Integer size);

  /**
   * Service for follow or unfollow a technologies.
   *
   * @param technology entity technology.
   * @param user entity user.
   * @return Technology info or message error.
   * @throws BadRequestException in case a request with problem were made.
   * @throws InternalServerErrorException in case some internal server error occur.
   * @throws NotFoundException when entity is not found.
   */
  public Technology followTechnology(Technology technology, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException;
}
