package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.RecommendationEnums;
import com.ciandt.techgallery.service.enums.TechnologyOrderOptionEnum;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologiesResponse;
import com.ciandt.techgallery.service.model.TechnologyFilter;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.ciandt.techgallery.service.util.TechnologyConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Services for Technology Endpoint requests.
 * 
 * @author felipers
 *
 */
public class TechnologyServiceImpl implements TechnologyService {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyServiceImpl instance;

  /** tech gallery user service */
  UserServiceTG userService = UserServiceTGImpl.getInstance();
  TechnologyDAO technologyDAO = TechnologyDAOImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyServiceImpl() {}

  public static TechnologyServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
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
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BE_BLANK.message());
    }
    // technology name can't be null or empty
    if (techName == null || techName.equals("")) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BE_BLANK.message());
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
   * 
   * @throws NotFoundException .
   */
  @Override
  public Response getTechnologies() throws InternalServerErrorException, NotFoundException {
    List<Technology> techEntities = technologyDAO.findAll();
    // if list is null, return a not found exception
    if (techEntities == null) {
      throw new NotFoundException(ValidationMessageEnums.NO_TECHNOLOGY_WAS_FOUND.message());
    } else {
      TechnologiesResponse response = new TechnologiesResponse();
      List<TechnologyResponse> internList = TechnologyConverter.fromEntityToTransient(techEntities);
      response.setTechnologies(internList);
      return response;
    }
  }

  private List<Technology> sortTechnologies(List<Technology> techList,
      TechnologyOrderOptionEnum orderBy) {
    switch (orderBy) {
      case POSITIVE_RECOMENDATION_QUANTITY:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getPositiveRecomendationsCounter(),
                counter1.getPositiveRecomendationsCounter());
          }
        });
        break;
      case NEGATIVE_RECOMENDATION_QUANTITY:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getNegativeRecomendationsCounter(),
                counter1.getNegativeRecomendationsCounter());
          }
        });
        break;
      case COMENTARY_QUANTITY:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getCommentariesCounter(),
                counter1.getCommentariesCounter());
          }
        });
        break;
      case ENDORSEMENT_QUANTITY:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getEndorsedsCounter(), counter1.getEndorsedsCounter());
          }
        });
        break;
      default:
        break;
    }
    return techList;
  }

  /**
   * GET for getting one technology.
   */
  @Override
  public Response getTechnology(final String id) throws NotFoundException {
    Technology techEntity = technologyDAO.findById(id);
    // if technology is null, return a not found exception
    if (techEntity == null) {
      throw new NotFoundException(ValidationMessageEnums.NO_TECHNOLOGY_WAS_FOUND.message());
    } else {
      TechnologyResponse response = TechnologyConverter.fromEntityToTransient(techEntity);
      return response;
    }
  }

  @Override
  public Response findTechnologiesByFilter(TechnologyFilter techFilter, User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    validateUser(user);
    if (techFilter.getRecommendationIs() != null
        && techFilter.getRecommendationIs().equals(RecommendationEnums.UNINFORMED.message())) {
      techFilter.setRecommendationIs("");
    }

    List<Technology> completeList = technologyDAO.findAll();
    List<Technology> filteredList = new ArrayList<>();
    verifyFilters(techFilter, completeList, filteredList);

    if (filteredList.isEmpty()) {
      return new TechnologiesResponse();
    } else {
      if (techFilter.getOrderOptionIs() != null && !techFilter.getOrderOptionIs().isEmpty()) {
        filteredList = sortTechnologies(filteredList,
            TechnologyOrderOptionEnum.fromString(techFilter.getOrderOptionIs()));
      }
      TechnologiesResponse response = new TechnologiesResponse();
      List<TechnologyResponse> internList = TechnologyConverter.fromEntityToTransient(filteredList);
      response.setTechnologies(internList);
      return response;
    }
  }

  private void verifyFilters(TechnologyFilter techFilter, List<Technology> completeList,
      List<Technology> filteredList) {
    for (Technology technology : completeList) {
      if (verifyTitleAndShortDescriptionFilter(techFilter, technology)) {
        if (techFilter.getRecommendationIs() != null) {
          if (verifyRecommendationFilter(techFilter, technology)) {
            filteredList.add(technology);
          } else {
            continue;
          }
        } else {
          filteredList.add(technology);
          continue;
        }
      } else if (verifyRecommendationFilter(techFilter, technology)
          && techFilter.getTitleContains() == null) {
        filteredList.add(technology);
        continue;
      }
    }
  }

  private boolean verifyRecommendationFilter(TechnologyFilter techFilter, Technology technology) {
    if (technology.getRecommendation() == null) {
      return true;
    } else if (techFilter.getRecommendationIs() != null && (technology.getRecommendation()
        .toLowerCase().equals(techFilter.getRecommendationIs().toLowerCase())
        || techFilter.getRecommendationIs().toLowerCase()
            .equals(RecommendationEnums.ANY.message().toLowerCase()))) {
      return true;
    }
    return false;
  }

  private boolean verifyTitleAndShortDescriptionFilter(TechnologyFilter techFilter,
      Technology technology) {
    if (techFilter.getTitleContains() != null
        && (technology.getName().toLowerCase().contains(techFilter.getTitleContains().toLowerCase())
            || technology.getShortDescription().toLowerCase()
                .contains(techFilter.getShortDescriptionContains().toLowerCase()))) {
      return true;
    }
    return false;
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
   * @throws InternalServerErrorException
   * @throws NotFoundException
   * @throws BadRequestException .
   */
  private void validateUser(User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

  @Override
  public List<String> getOrderOptions(User user) {
    List<String> orderOptions = new ArrayList<String>();
    for (TechnologyOrderOptionEnum item : TechnologyOrderOptionEnum.values()) {
      orderOptions.add(item.option());
    }
    return orderOptions;
  }

  @Override
  public void addCommentariesCounter(Technology entity) {
    if (entity != null) {
      entity.addCommentariesCounter();
    }
    technologyDAO.update(entity);
  }

  @Override
  public void removeCommentariesCounter(Technology entity) {
    if (entity != null) {
      entity.removeCommentariesCounter();
    }
    technologyDAO.update(entity);

  }

  @Override
  public void addRecomendationCounter(Technology entity, Boolean score) {
    if (entity == null) {
      return;
    }
    if (score) {
      entity.addPositiveRecomendationsCounter();
    } else {
      entity.addNegativeRecomendationsCounter();
    }
    technologyDAO.update(entity);
  }

  @Override
  public void removeRecomendationCounter(Technology entity, Boolean score) {
    if (entity == null) {
      return;
    }
    if (score) {
      entity.removePositiveRecomendationsCounter();
    } else {
      entity.removeNegativeRecomendationsCounter();
    }
    technologyDAO.update(entity);
  }

  @Override
  public void updateEdorsedsCounter(Technology technology, Integer size) {
    technology.setEndorsedsCounter(size);
    technologyDAO.update(technology);
  }
}
