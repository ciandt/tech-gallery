package com.ciandt.techgallery.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ciandt.techgallery.service.model.EndorsementEntityResponse;
import com.ciandt.techgallery.service.model.EndorsementResponse;
import com.ciandt.techgallery.service.model.EndorsementsGroupedByEndorsedTransient;
import com.ciandt.techgallery.service.model.EndorsementsResponse;
import com.ciandt.techgallery.service.model.Response;
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
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException {
    // endorser user google id
    String googleId;
    // endorser user email
    String endorserEmail;
    // endorser user from techgallery datastore
    TechGalleryUser tgEndorserUser;
    // endorsed user from techgallery datastore
    TechGalleryUser tgEndorsedUser;
    // endorsed email
    String endorsedEmail;
    // technology id
    String technologyId;
    // technology from techgallery datastore
    Technology technology;

    // User from endpoint (endorser) can't be null
    if (user == null) {
      throw new OAuthRequestException("OAuth error, null user reference!");
    } else {
      googleId = user.getUserId();
      endorserEmail = user.getEmail();
    }

    // TechGalleryUser can't be null and must exists on datastore
    if (googleId == null || googleId.equals("")) {
      throw new NotFoundException("Current user was not found!");
    } else {
      // get the TechGalleryUser from datastore or PEOPLE API
      tgEndorserUser = userService.getUserSyncedWithProvider(endorserEmail.replace("@ciandt.com", ""));
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
    // user cannot endorse itself
    if (tgEndorserUser.getId() == tgEndorsedUser.getId()) {
      throw new BadRequestException("You cannot endorse yourself!");
    }
    // user cannot endorse the same people twice
    if (endorsementDAO.findByUsers(tgEndorserUser, tgEndorsedUser, technology).size() > 0) {
      throw new BadRequestException("You already endorsed this user for this technology");
    }
    // create endorsement and save it
    Endorsement entity = new Endorsement();
    entity.setEndorser(Ref.create(tgEndorserUser));
    entity.setEndorsed(Ref.create(tgEndorsedUser));
    entity.setTimestamp(new Date());
    entity.setTechnology(Ref.create(technology));
    endorsementDAO.add(entity);
    // return the added entity
    return getEndorsement(entity.getId());
  }

  /**
   * GET for getting all endorsements.
   */
  @Override
  public Response getEndorsements() throws InternalServerErrorException, NotFoundException {
    List<Endorsement> endrsEntities = endorsementDAO.findAll();
    // if list is null, return a not found exception
    if (endrsEntities == null) {
      throw new NotFoundException("No endorsement was found.");
    } else {
      EndorsementsResponse response = new EndorsementsResponse();
      List<EndorsementEntityResponse> internList = new ArrayList<EndorsementEntityResponse>();

      for (int i = 0; i < endrsEntities.size(); i++) {
        Endorsement entity = endrsEntities.get(i);
        EndorsementEntityResponse endrsResponseItem = new EndorsementEntityResponse();
        endrsResponseItem.setId(entity.getId());
        endrsResponseItem.setEndorser(entity.getEndorserEntity());
        endrsResponseItem.setEndorsed(entity.getEndorsedEntity());
        endrsResponseItem.setTimestamp(entity.getTimestamp());
        endrsResponseItem.setTechnology(entity.getTechnologyEntity());
        internList.add(endrsResponseItem);
      }
      response.setEndorsements(internList);
      return response;
    }
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
      EndorsementEntityResponse response = new EndorsementEntityResponse();
      response.setId(endorseEntity.getId());
      response.setId(endorseEntity.getId());
      response.setEndorser(endorseEntity.getEndorserEntity());
      response.setEndorsed(endorseEntity.getEndorsedEntity());
      response.setTimestamp(endorseEntity.getTimestamp());
      response.setTechnology(endorseEntity.getTechnologyEntity());
      return response;
    }
  }

  @Override
  public List<EndorsementsGroupedByEndorsedTransient> groupEndorsementByEndorsed(
      List<Endorsement> endorsements) {

    Map<TechGalleryUser, List<TechGalleryUser>> mapUsersGrouped = new HashMap<TechGalleryUser, List<TechGalleryUser>>();
    
    for (Endorsement endorsement : endorsements) {
      TechGalleryUser endorsed = endorsement.getEndorsedEntity();
      
      if (mapUsersGrouped.containsKey(endorsed)){
        List<TechGalleryUser> endorsersList = mapUsersGrouped.get(endorsed);
        endorsersList.add(endorsement.getEndorserEntity());
        mapUsersGrouped.put(endorsed, endorsersList);
      } else {
        List<TechGalleryUser> endorsersList = new ArrayList<TechGalleryUser>();
        endorsersList.add(endorsement.getEndorserEntity());
        mapUsersGrouped.put(endorsed, endorsersList);
      }
    }
    return transformGroupedUserMapIntoList(mapUsersGrouped);
  }

  private List<EndorsementsGroupedByEndorsedTransient> transformGroupedUserMapIntoList(
      Map<TechGalleryUser, List<TechGalleryUser>> mapUsersGrouped) {
    List<EndorsementsGroupedByEndorsedTransient> groupedList = new ArrayList<EndorsementsGroupedByEndorsedTransient>();
    
    for(Map.Entry<TechGalleryUser, List<TechGalleryUser>> entry : mapUsersGrouped.entrySet()){
      EndorsementsGroupedByEndorsedTransient grouped = new EndorsementsGroupedByEndorsedTransient();
      grouped.setEndorsed(entry.getKey());
      grouped.setEndorsers(entry.getValue());
      groupedList.add(grouped);
    }
    return groupedList;
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
