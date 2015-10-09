package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.EndorsementsGroupedByEndorsedTransient;
import com.ciandt.techgallery.service.model.Response;

import java.util.List;

/**
 * Services for Endorsements.
 * 
 * @author felipers
 *
 */
public interface EndorsementService {

  /**
   * Service for adding a endorsement.
   * 
   * @param endorsement json with endorsement info.
   * @param user current user logged.
   * @return endorsement info or message error.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  Endorsement addOrUpdateEndorsement(final EndorsementResponse endorsement, final User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException;

  /**
   * Service for adding a endorsement in +1 button.
   * 
   * @param endorsement json with endorsement info.
   * @param user current user logged.
   * @return endorsement info or message error.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  Endorsement addOrUpdateEndorsementPlusOne(final EndorsementResponse endorsement, final User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException;

  /**
   * Service for getting all endorsements.
   * 
   * @return endorsements info or message error.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   */
  Response getEndorsements() throws InternalServerErrorException, NotFoundException;

  /**
   * Service for getting a endorsement.
   * 
   * @param id entity id.
   * @return endorsement
   * @throws NotFoundException in case the information are not founded
   */
  Endorsement getEndorsement(final Long id) throws NotFoundException;

  /**
   * Service for getting a endorsement.
   * 
   * @param endorsements List of Endorsement.
   * @param techId technology id.
   * @return List of endorsements
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  List<EndorsementsGroupedByEndorsedTransient> groupEndorsementByEndorsed(
      List<Endorsement> endorsements, String techId) throws BadRequestException, NotFoundException,
          InternalServerErrorException, OAuthRequestException;

  /**
   * Service for getting all endorsements of a Technology.
   * 
   * @param techId technology entity id.
   * @param user Technology user
   * @return endorsement
   * @throws InternalServerErrorException in case something goes wrong
   * @throws OAuthRequestException in case of authentication problem
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  Response getEndorsementsByTech(final String techId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException;

}
