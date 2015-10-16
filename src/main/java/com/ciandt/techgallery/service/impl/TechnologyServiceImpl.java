package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.persistence.dao.StorageDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.dao.storage.StorageDAOImpl;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

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

  /** tech gallery user service. */
  UserServiceTG userService = UserServiceTGImpl.getInstance();
  TechnologyDAO technologyDAO = TechnologyDAOImpl.getInstance();
  StorageDAO storageDAO = StorageDAOImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 07/10/2015
   *
   * @return TechnologyServiceImpl instance.
   */
  public static TechnologyServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public Technology addTechnology(Technology technology, User user)
      throws BadRequestException, IOException, GeneralSecurityException {

    validateInformations(technology);
    String imageLink = technology.getImage();
    if (technology.getRecommendation() == null) {
      imageLink = storageDAO.insertImage(convertNameToId(technology.getName()),
          new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(technology.getImage())));
    }
    fillTechnology(technology, user, imageLink);
    technologyDAO.add(technology);

    return technology;
  }

  /**
   * Fill a few informations about the technology.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param technology to be converted.
   * @param user to get informations.
   * @param imageLink returned by the cloud storage.
   *
   */
  private void fillTechnology(Technology technology, User user, String imageLink) {
    technology.setId(convertNameToId(technology.getName()));
    if (user != null && user.getEmail() != null) {
      technology.setAuthor(user.getEmail());
    }
    technology.setCreationDate(new Date());
    technology.setImage(imageLink);
    technology.initCounters();
  }

  /**
   * Method that gets the name of the technology and creates the id.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param name to format.
   *
   * @return the id formatted.
   */
  private String convertNameToId(String name) {
    name = Normalizer.normalize(name, Normalizer.Form.NFD);
    name = name.replaceAll("[^\\p{ASCII}]", "");
    return name.toLowerCase().replaceAll(" ", "_");
  }

  /**
   * Method to validade informations of the technology to be added.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param technology to be validated.
   *
   * @throws BadRequestException in case a request with problem were made.
   */
  private void validateInformations(Technology technology) throws BadRequestException {
    if (technology.getId() == null || technology.getId().equals("")) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BLANK.message());
    } else if (technology.getName() == null || technology.getName().equals("")) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NAME_CANNOT_BLANK.message());
    } else if (technology.getShortDescription() == null
        || technology.getShortDescription().equals("")) {
      throw new BadRequestException(
          ValidationMessageEnums.TECHNOLOGY_SHORT_DESCRIPTION_BLANK.message());
    } else if (technology.getDescription() == null || technology.getDescription().equals("")) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_DESCRIPTION_BLANK.message());
    }

    Technology dbTechnology = technologyDAO.findByName(technology.getName());
    if (dbTechnology != null) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NAME_ALREADY_USED.message());
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
      response.setTechnologies(techEntities);
      return response;
    }
  }

  private List<Technology> sortTechnologies(List<Technology> techList,
      TechnologyOrderOptionEnum orderBy) {
    switch (orderBy) {
      case POSITIVE_RECOMMENDATION_AMOUNT:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getPositiveRecommendationsCounter(),
                counter1.getPositiveRecommendationsCounter());
          }
        });
        break;
      case NEGATIVE_RECOMMENDATION_AMOUNT:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getNegativeRecommendationsCounter(),
                counter1.getNegativeRecommendationsCounter());
          }
        });
        break;
      case COMMENT_AMOUNT:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getCommentariesCounter(),
                counter1.getCommentariesCounter());
          }
        });
        break;
      case ENDORSEMENT_AMOUNT:
        Collections.sort(techList, new Comparator<Technology>() {
          @Override
          public int compare(Technology counter1, Technology counter2) {
            return Integer.compare(counter2.getEndorsersCounter(), counter1.getEndorsersCounter());
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
  public Technology getTechnology(final String id) throws NotFoundException {
    Technology techEntity = technologyDAO.findById(id);
    // if technology is null, return a not found exception
    if (techEntity == null) {
      throw new NotFoundException(ValidationMessageEnums.NO_TECHNOLOGY_WAS_FOUND.message());
    } else {
      return techEntity;
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
    if ((techFilter.getTitleContains() == null || techFilter.getTitleContains().isEmpty())
        && (techFilter.getRecommendationIs() == null
            || techFilter.getRecommendationIs().isEmpty())) {
      filteredList.addAll(completeList);
    } else {
      verifyFilters(techFilter, completeList, filteredList);
    }

    if (filteredList.isEmpty()) {
      return new TechnologiesResponse();
    } else {
      if (techFilter.getOrderOptionIs() != null && !techFilter.getOrderOptionIs().isEmpty()) {
        filteredList = sortTechnologies(filteredList,
            TechnologyOrderOptionEnum.fromString(techFilter.getOrderOptionIs()));
      }
      TechnologiesResponse response = new TechnologiesResponse();
      response.setTechnologies(filteredList);
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
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  private void validateUser(User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
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
      entity.addPositiveRecommendationsCounter();
    } else {
      entity.addNegativeRecommendationsCounter();
    }
    technologyDAO.update(entity);
  }

  @Override
  public void removeRecomendationCounter(Technology entity, Boolean score) {
    if (entity == null) {
      return;
    }
    if (score) {
      entity.removePositiveRecommendationsCounter();
    } else {
      entity.removeNegativeRecommendationsCounter();
    }
    technologyDAO.update(entity);
  }

  @Override
  public void updateEdorsedsCounter(Technology technology, Integer size) {
    technology.setEndorsersCounter(size);
    technologyDAO.update(technology);
  }
}
