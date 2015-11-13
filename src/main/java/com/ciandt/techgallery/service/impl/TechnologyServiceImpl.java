package com.ciandt.techgallery.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;

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
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

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
  private TechnologyServiceImpl() {
  }

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros
   *         Moreira </a>
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
  public Technology addOrUpdateTechnology(Technology technology, User user)
      throws BadRequestException, IOException, GeneralSecurityException {

    Technology foundTechnology = validateInformations(technology);
    Boolean isUpdate = foundTechnology != null && foundTechnology.getId().equals(technology.getId())
        && foundTechnology.getActive().equals(Boolean.TRUE);

    String imageLink = technology.getImage();
    if (technology.getImageContent() != null) {
      imageLink = storageDAO.insertImage(convertNameToId(technology.getName()),
          new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(technology.getImageContent())));
    }

    fillTechnology(technology, user, imageLink, isUpdate);

    technologyDAO.add(technology);

    return technology;
  }

  /**
   * Fill a few informations about the technology.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros
   *         Moreira </a>
   * @since 13/10/2015
   *
   * @param technology
   *          to be converted.
   * @param user
   *          to get informations.
   * @param imageLink
   *          returned by the cloud storage.
   *
   */
  private void fillTechnology(Technology technology, User user, String imageLink, Boolean isUptate) {
    technology.setId(convertNameToId(technology.getName()));
    if (user != null && user.getEmail() != null) {
      if (!isUptate) {
        technology.setAuthor(user.getEmail());
      }
      technology.setLastActivityUser(user.getEmail());
    }
    technology.setActive(Boolean.TRUE);
    technology.setCreationDate(new Date());
    technology.setLastActivity(new Date());
    technology.setImage(imageLink);
    technology.initCounters();
  }

  /**
   * Method that gets the name of the technology and creates the id.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros
   *         Moreira </a>
   * @since 13/10/2015
   *
   * @param name
   *          to format.
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
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros
   *         Moreira </a>
   * @since 13/10/2015
   *
   * @param technology
   *          to be validated.
   *
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  private Technology validateInformations(Technology technology) throws BadRequestException {
    if (StringUtils.isBlank(technology.getId())) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BLANK.message());
    } else if (StringUtils.isBlank(technology.getName())) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NAME_CANNOT_BLANK.message());
    } else if (StringUtils.isBlank(technology.getShortDescription())) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_SHORT_DESCRIPTION_BLANK.message());
    } else if (StringUtils.isBlank(technology.getDescription())) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_DESCRIPTION_BLANK.message());
    }

    Technology dbTechnology = technologyDAO.findByName(technology.getName());
    if (dbTechnology != null && technology.getId() == null) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NAME_ALREADY_USED.message());
    }
    if (technology.getId() != null && dbTechnology != null && !dbTechnology.getName().equals(technology.getName())) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NAME_CANNOT_CHANGE.message());
    }
    return dbTechnology;
  }

  /**
   * GET for getting all technologies.
   *
   * @throws NotFoundException
   *           .
   */
  @Override
  public Response getTechnologies(User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    List<Technology> techEntities = technologyDAO.findAllActives();
    // if list is null, return a not found exception
    if (techEntities == null || techEntities.isEmpty()) {
      return new TechnologiesResponse();
    } else {
      verifyTechnologyFollowedByUser(user, techEntities);
      TechnologiesResponse response = new TechnologiesResponse();
      Technology.sortTechnologiesDefault(techEntities);
      response.setTechnologies(techEntities);
      return response;
    }
  }

  private List<Technology> setDateFilteredList(List<Technology> completeList, Date dateReference) {
    List<Technology> dateFilteredList = new ArrayList<>();
    for (Technology technology : completeList) {
      if (technology.getLastActivity().after(dateReference) || technology.getLastActivity().equals(dateReference)) {
        dateFilteredList.add(technology);
      }
    }
    return dateFilteredList;
  }

  private Date setDateReference(Date currentDate, int daysToSubtract) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, daysToSubtract);
    Date dateReference = cal.getTime();
    return dateReference;
  }

  @Override
  public Response findTechnologiesByFilter(TechnologyFilter techFilter, User user)
      throws InternalServerErrorException, NotFoundException, BadRequestException {
    validateUser(user);
    if (techFilter.getRecommendationIs() != null
        && techFilter.getRecommendationIs().equals(RecommendationEnums.UNINFORMED.message())) {
      techFilter.setRecommendationIs("");
    }
    List<Technology> completeList = technologyDAO.findAllActives();
    completeList = filterByLastActivityDate(techFilter, completeList);

    List<Technology> filteredList = new ArrayList<>();
    if (StringUtils.isBlank(techFilter.getTitleContains()) && techFilter.getRecommendationIs() == null) {
      filteredList.addAll(completeList);
    } else {
      verifyFilters(techFilter, completeList, filteredList);
    }

    if (filteredList.isEmpty()) {
      return new TechnologiesResponse();
    } else {
      if (techFilter.getOrderOptionIs() != null && !techFilter.getOrderOptionIs().isEmpty()) {
        filteredList = Technology.sortTechnologies(filteredList,
            TechnologyOrderOptionEnum.fromString(techFilter.getOrderOptionIs()));
      } else {
        Technology.sortTechnologiesDefault(filteredList);
      }
      TechnologiesResponse response = new TechnologiesResponse();
      response.setTechnologies(filteredList);
      return response;
    }
  }

  private List<Technology> filterByLastActivityDate(TechnologyFilter techFilter, List<Technology> completeList) {
    List<Technology> dateFilteredList = new ArrayList<>();
    if (techFilter.getDateFilter() != null) {
      Date currentDate = new Date();
      switch (techFilter.getDateFilter()) {
      case LAST_DAY:
        Date lastDay = setDateReference(currentDate, -1);
        dateFilteredList = setDateFilteredList(completeList, lastDay);
        break;

      case LAST_7_DAYS:
        Date last7Days = setDateReference(currentDate, -7);
        dateFilteredList = setDateFilteredList(completeList, last7Days);
        break;

      case LAST_30_DAYS:
        Date last30Days = setDateReference(currentDate, -30);
        dateFilteredList = setDateFilteredList(completeList, last30Days);
        break;
      default:
        break;
      }
      completeList = dateFilteredList;
    }
    return completeList;
  }

  private void verifyTechnologyFollowedByUser(User user, List<Technology> filteredList)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser.getFollowedTechnologyIds() != null && !techUser.getFollowedTechnologyIds().isEmpty()) {
      for (Technology technology : filteredList) {
        if (techUser.getFollowedTechnologyIds().contains(technology.getId())) {
          technology.setFollowedByUser(true);
        }
      }
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
      } else if (verifyRecommendationFilter(techFilter, technology) && techFilter.getTitleContains() == null) {
        filteredList.add(technology);
        continue;
      }
    }
  }

  private boolean verifyRecommendationFilter(TechnologyFilter techFilter, Technology technology) {
    if (technology.getRecommendation() == null && techFilter.getRecommendationIs() == "") {
      return true;
    } else if (technology.getRecommendation() != null && techFilter.getRecommendationIs() != null
        && (technology.getRecommendation().toLowerCase().equals(techFilter.getRecommendationIs().toLowerCase())
            || techFilter.getRecommendationIs().toLowerCase()
                .equals(RecommendationEnums.ANY.message().toLowerCase()))) {
      return true;
    }
    return false;
  }

  private boolean verifyTitleAndShortDescriptionFilter(TechnologyFilter techFilter, Technology technology) {
    if (techFilter.getTitleContains() != null
        && (technology.getName().toLowerCase().contains(techFilter.getTitleContains().toLowerCase()) || technology
            .getShortDescription().toLowerCase().contains(techFilter.getShortDescriptionContains().toLowerCase()))) {
      return true;
    }
    return false;
  }

  @Override
  public Technology getTechnologyById(String id, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    Technology tech = technologyDAO.findByIdActive(id);
    if (tech == null) {
      throw new NotFoundException(ValidationMessageEnums.TECHNOLOGY_NOT_EXIST.message());
    } else {
      if (user != null) {
        TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
        if (techUser.getFollowedTechnologyIds() != null && techUser.getFollowedTechnologyIds().contains(tech.getId())) {
          tech.setFollowedByUser(true);
        }
      }
      return tech;
    }
  }

  /**
   * Validate the user logged in.
   *
   * @param user
   *          info about user from google
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  private void validateUser(User user) throws BadRequestException, NotFoundException, InternalServerErrorException {

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

  @Override
  public void audit(String technologyId, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    Technology technology = getTechnologyById(technologyId, user);
    technology.setLastActivity(new Date());
    technology.setLastActivityUser(user.getEmail());
    technologyDAO.update(technology);
  }

  @Override
  public Technology deleteTechnology(String technologyId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
    validateUser(user);
    Technology technology = technologyDAO.findById(technologyId);
    if (technology == null) {
      throw new NotFoundException(ValidationMessageEnums.NO_TECHNOLOGY_WAS_FOUND.message());
    }
    technology.setActive(Boolean.FALSE);
    technology.setLastActivity(new Date());
    technology.setLastActivityUser(user.getEmail());
    technologyDAO.update(technology);
    return technology;
  }
}
