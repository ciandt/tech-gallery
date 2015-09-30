package com.ciandt.techgallery.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologiesResponse;
import com.ciandt.techgallery.service.model.TechnologyFilter;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.ciandt.techgallery.service.util.TechnologyConverter;
import com.ciandt.techgallery.utils.i18n.I18n;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

/**
 * Services for Technology Endpoint requests.
 * 
 * @author felipers
 *
 */
public class TechnologyServiceImpl implements TechnologyService {

  private static final I18n i18n = I18n.getInstance();
  TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();
  TechnologyDAO technologyDAO = new TechnologyDAOImpl();

  /**
   * POST for adding a technology.
   */
  @Override
  public Response addTechnology(final TechnologyResponse technology)
      throws InternalServerErrorException, BadRequestException {
    String techId = technology.getId();
    String techName = technology.getName();

    // technology id can't be null or empty
    if (techId == null || techId.equals("")) {
      throw new BadRequestException(i18n.t("Technology's id cannot be blank."));
    }
    // technology name can't be null or empty
    if (techName == null || techName.equals("")) {
      throw new BadRequestException(i18n.t("Technology's name cannot be blank."));
    } else {
      Technology entity = TechnologyConverter.fromTransientToEntity(technology);
      technologyDAO.add(entity);
      // set the id and return it
      technology.setId(entity.getId());
      return technology;
    }
  }

  /**
   * GET for getting all technologies.
   */
  @Override
  public Response getTechnologies() throws InternalServerErrorException, NotFoundException {
    List<Technology> techEntities = technologyDAO.findAll();
    // if list is null, return a not found exception
    if (techEntities == null) {
      throw new NotFoundException(i18n.t("No technology was found."));
    } else {
      TechnologiesResponse response = new TechnologiesResponse();
      List<TechnologyResponse> internList = TechnologyConverter.fromEntityToTransient(techEntities);
      response.setTechnologies(internList);
      return response;
    }
  }

  /**
   * GET for getting one technology.
   */
  @Override
  public Response getTechnology(final String id) throws NotFoundException {
    Technology techEntity = technologyDAO.findById(id);
    // if technology is null, return a not found exception
    if (techEntity == null) {
      throw new NotFoundException(i18n.t("No technology was found."));
    } else {
      TechnologyResponse response = TechnologyConverter.fromEntityToTransient(techEntity);
      return response;
    }
  }

  @Override
  public Response findTechnologiesByFilter(TechnologyFilter techFilter, User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    
    validateUser(user);
    List<Technology> completeList = technologyDAO.findAll();
    List<Technology> filteredList = new ArrayList<>();
    for (Technology technology : completeList) {
      if(technology.getName().toLowerCase().contains(techFilter.getTitleContains().toLowerCase()) || 
          technology.getShortDescription().toLowerCase().contains(techFilter.getShortDescriptionContains().toLowerCase())){
        filteredList.add(technology);
      }
    }
    if (filteredList.isEmpty()) {
      return new TechnologiesResponse();
    } else {
      TechnologiesResponse response = new TechnologiesResponse();
      List<TechnologyResponse> internList = TechnologyConverter.fromEntityToTransient(filteredList);
      response.setTechnologies(internList);
      return response;
    }
  }

  @Override
  public Technology getTechnologyById(String id) throws NotFoundException {
    Technology tech = technologyDAO.findById(id);
    if (tech == null) {
      throw new NotFoundException(ValidationMessageEnums.TECHNOLOGY_NOT_EXIST.message());
    } else {
      return tech;
    }

  }

  /**
   * Validate the user logged in.
   * 
   * @param user info about user from google
   * @throws BadRequestException .
   */
  private void validateUser(User user) throws BadRequestException {
    
    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }
    
    TechGalleryUser techUser = techGalleryUserDAO.findByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }
}
