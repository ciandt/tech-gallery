package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.TechnologyLink;
import com.ciandt.techgallery.service.model.Response;

/**
 * TechnologyLinkService Interface.
 *
 * @author Sidharta Noleto
 *
 */
public interface TechnologyLinkService {

  /**
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  TechnologyLink addLink(TechnologyLink link, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException;

  /**
   * @param techId technology entity id.
   * @return link or message error.
   * @throws InternalServerErrorException .
   * @throws BadRequestException .
   */
  Response getLinksByTech(final String techId, User user) throws InternalServerErrorException,
      BadRequestException, NotFoundException, OAuthRequestException;

  /**
   * @return link or message error.
   * @throws InternalServerErrorException .
   * @throws BadRequestException .
   */
  TechnologyLink deleteLink(final Long linkId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException;

  /**
   * @throws NotFoundException in case the link does not exist
   */
  TechnologyLink getById(Long id) throws NotFoundException;
}
