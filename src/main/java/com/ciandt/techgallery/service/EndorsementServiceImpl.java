package com.ciandt.techgallery.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
import com.ciandt.techgallery.service.model.EndorsementsGroupedByEndorsedTransient;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * Services for Endorsement Endpoint requests.
 * 
 * @author felipers
 *
 */
public class EndorsementServiceImpl implements EndorsementService {

  @SuppressWarnings("unused")
  private static final Logger log = Logger.getLogger(EndorsementServiceImpl.class.getName());

  /** endorsement dao. */
  EndorsementDAO endorsementDAO = new EndorsementDAOImpl();
  /** user dao for getting users. */
  UserDAO userDAO = new UserDAOImpl();
  /** technology dao for getting technologies. */
  TechnologyDAO techDAO = new TechnologyDAOImpl();
  /** tech gallery user service for getting PEOPLE API user. */
  UserServiceTG userService = new UserServiceTGImpl();

  /**
   * POST for adding a endorsement.
   * 
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws OAuthRequestException 
   */
  @Override
  public Response addOrUpdateEndorsement(EndorsementResponse endorsement, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    // endorser user google id
    String googleId;
    // endorser user from techgallery datastore
    TechGalleryUser tgEndorserUser;
    // endorsed user from techgallery datastore
    TechGalleryUser tgEndorsedUser;
    // endorsed email
    String endorsedEmail;
    // user json info from PEOPLE API
    UserResponse peopleUser;
    // technology id
    String technologyId;
    // technology from techgallery datastore
    Technology technology;

    // User from endpoint (endorser) can't be null
    if (user == null) {
      throw new OAuthRequestException("oauth error, user reference null");
    } else {
      googleId = user.getUserId();
    }

    // TechGalleryUser can't be null and must exists on datastore
    if (googleId == null || googleId.equals("")) {
      throw new NotFoundException("Current user was not found!");
    } else {
      // get the TechGalleryUser from datastore
      tgEndorserUser = userDAO.findByGoogleId(googleId);
      if (tgEndorserUser == null) {
        throw new BadRequestException("Endorser user do not exists on datastore!");
      }
    }

    // endorsed email can't be null.
    endorsedEmail = endorsement.getEndorsed();
    if (endorsedEmail == null || endorsedEmail.equals("")) {
      throw new BadRequestException("Endorsed email was not especified!");
    } else {
      // get user from PEOPLE
      tgEndorsedUser = userService.getUserSyncedWithProvider(endorsedEmail);
      if (tgEndorsedUser == null) {
        throw new BadRequestException("Endorsed email was not found on PEOPLE!");
      }
    }

    // technology id can't be null and must exists on datastore
    technologyId = endorsement.getTechnology();
    if (technologyId == null || technologyId.equals("")) {
      throw new BadRequestException("Technology was not especified!");
    } else {
      technology = techDAO.findById(technologyId);
      if (technology == null) {
        throw new BadRequestException("Technology do not exists!");
      }
    }

    // final checks and persist
    Endorsement entity = new Endorsement();
    entity.setEndorser(Ref.create(tgEndorserUser));
    entity.setEndorsed(Ref.create(tgEndorsedUser));
    entity.setTimestamp(new Date());
    entity.setTechnology(Ref.create(technology));
    endorsementDAO.add(entity);
    // set the id and return it
    endorsement.setId(entity.getId());
    return endorsement;
  }

  /**
   * GET for getting all endorsements.
   */
  @Override
  public Response getEndorsements() throws InternalServerErrorException, NotFoundException {
    throw new InternalServerErrorException("Not yet implemented!");
  }

  /**
   * GET for getting one endorsement.
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

  @Override
  public List<EndorsementsGroupedByEndorsedTransient> groupEndorsementByEndorsed(
      List<Endorsement> endorsements) {
    // TODO damorim
    
    return null;
  }

  @Override
  public Response addEndorsementTest() {

    log.info("addEndorsementTest: adding some endorsements for test");

    for (int i = 1; i <= 10; i++) {

      TechGalleryUser endorser = new TechGalleryUser();
      endorser.setName("endorser name" + i);
      Key<TechGalleryUser> keyEndorser = userDAO.add(endorser);


      TechGalleryUser endorsed = new TechGalleryUser();
      endorsed.setName("endorsed name" + i);
      Key<TechGalleryUser> keyEndorsed = userDAO.add(endorsed);

      Technology tech = new Technology();
      tech.setId("tech" + i);
      tech.setName("tech name" + i);
      Key<Technology> keyTech = techDAO.add(tech);

      Endorsement endorsment = new Endorsement();
      endorsment.setEndorser(Ref.create(keyEndorser));
      endorsment.setEndorsed(Ref.create(keyEndorsed));
      endorsment.setTechnology(Ref.create(keyTech));

      endorsementDAO.add(endorsment);
    }

    return null;
  }

  @Override
  public Response getEndorsementTest() {
    log.info("entering in getEndorsementTest for test");

    for (int i = 1; i <= 10; i++) {
      log.info("Fetching endorsement list by technology");
      Technology tech = new Technology();
      tech.setId("tech" + i);
      List<Endorsement> list = endorsementDAO.findAllByTechnology(tech);

      for (Endorsement end : list) {
        log.info("Endorsment id: " + end.getId());
        log.info("EndorsedEntity name: " + end.getEndorsedEntity().getName());
        log.info("EndorserEntity name: " + end.getEndorserEntity().getName());
        log.info("EndorserEntity tech: " + end.getTechnologyEntity().getName());
      }
    }
    return null;
  }
}
