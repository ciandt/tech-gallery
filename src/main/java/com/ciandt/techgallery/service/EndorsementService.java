package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

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
   * @return endorsement info or message error.
   * @throws InternalServerErrorException
   * @throws BadRequestException 
   */
  public Response addOrUpdateEndorsement(final EndorsementResponse endorsement)
      throws InternalServerErrorException, BadRequestException;

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
}
