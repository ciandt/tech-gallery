package com.ciandt.techgallery.service;

import java.util.List;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.EndorsementsGroupedByEndorsedTransient;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

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
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws OAuthRequestException 
   */
  public Response addOrUpdateEndorsement(final EndorsementResponse endorsement, final User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException;
  
  /**
   * Service for adding a endorsement in +1 button.
   * 
   * @param endorsement json with endorsement info.
   * @param user current user logged.
   * @return endorsement info or message error.
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws OAuthRequestException 
   */
  public Response addOrUpdateEndorsementPlusOne(final EndorsementResponse endorsement, final User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException;

  /**
   * Service for getting all endorsements.
   * 
   * @return endorsements info or message error.
   * @throws InternalServerErrorException
   * @throws NotFoundException
   */
  public Response getEndorsements() throws InternalServerErrorException, NotFoundException;

  /**
   * Service for getting a endorsement.
   * 
   * @param id entity id.
   * @return
   * @throws NotFoundException
   */
  public Response getEndorsement(final Long id) throws NotFoundException;

  /**
   * Service for getting a endorsement.
   * 
   * @param endorsements List of Endorsement.
   * @param techId technology id.
   * @return
   * @throws OAuthRequestException 
   * @throws InternalServerErrorException 
   * @throws NotFoundException 
   * @throws BadRequestException 
   */
  public List<EndorsementsGroupedByEndorsedTransient> groupEndorsementByEndorsed(
      List<Endorsement> endorsements, String techId) throws BadRequestException, NotFoundException, InternalServerErrorException, OAuthRequestException;

  /**
   * Service for getting all endorsements of a Technology.
   * 
   * @param techId technology entity id.
   * @return
   * @throws InternalServerErrorException 
   * @throws OAuthRequestException 
   * @throws NotFoundException 
   * @throws BadRequestException 
   */
  public Response getEndorsementsByTech(final String techId, User user) throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException;
  
}
