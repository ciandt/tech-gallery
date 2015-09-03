package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.dao.EndorsementDAO;
import com.ciandt.techgallery.persistence.dao.EndorsementDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.dao.UserDAO;
import com.ciandt.techgallery.persistence.dao.UserDAOImpl;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.Response;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

/**
 * Services for Endorsement Endpoint requests.
 * 
 * @author felipers
 *
 */
public class EndorsementServiceImpl implements EndorsementService {

  /** endorsement dao. */
  EndorsementDAO endorsementDAO = new EndorsementDAOImpl();
  /** user dao for getting users. */
  UserDAO userDAO = new UserDAOImpl();
  /** technology dao for getting technologies. */
  TechnologyDAO techDAO = new TechnologyDAOImpl();

  /**
   * POST for adding a endorsement.
   * 
   * @throws InternalServerErrorException
   * @throws BadRequestException
   */
  @Override
  public Response addOrUpdateEndorsement(EndorsementResponse endorsement)
      throws InternalServerErrorException, BadRequestException {
    // TechGalleryUser id can't be null and must exists on datastore
    Long endorserId = endorsement.getEndorser();
    TechGalleryUser tgUser;
    if (endorserId == null) {
      throw new BadRequestException("Endorser user was not especified!");
    } else {
      tgUser = userDAO.findById(endorserId);
      if (tgUser == null) {
        throw new BadRequestException("Endorser user do not exists!");
      }
    }

    // endorsed email can't be null.
    String endorsed = endorsement.getEndorsed();
    if (endorsed == null || endorsed.equals("")) {
      throw new BadRequestException("Endorsed user was not especified!");
    }

    // technology id or name? can't be null and must exists on datastore
    String technologyId = endorsement.getTechnology();
    Technology technology;
    if (technologyId == null || technologyId.equals("")) {
      throw new BadRequestException("Technology was not especified!");
    } else {
      technology = techDAO.findById(technologyId);
      if (technology == null) {
        throw new BadRequestException("Technology user do not exists!");
      }
    }

    throw new InternalServerErrorException("Not yet implemented!");
  }

  /**
   * GET for getting all technologies.
   */
  @Override
  public Response getEndorsements() throws InternalServerErrorException, NotFoundException {
    throw new InternalServerErrorException("Not yet implemented!");
  }

  /**
   * GET for getting one technology.
   */
  @Override
  public Response getEndorsement(Long id) throws NotFoundException {
    Endorsement endorseEntity = endorsementDAO.findById(id);
    // if technology is null, return a not found exception
    if (endorseEntity == null) {
      throw new NotFoundException("No endorsement was found.");
    } else {
      EndorsementResponse response = new EndorsementResponse();
      response.setId(endorseEntity.getId());
      response.setTimestamp(endorseEntity.getTimestamp());
      return response;
    }
  }
}

