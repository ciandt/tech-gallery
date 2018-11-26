package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.TechnologyLinkDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyLinkDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyLink;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;
import com.ciandt.techgallery.service.TechnologyLinkService;
import com.ciandt.techgallery.service.TechnologyFollowersService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyLinksTO;
import com.ciandt.techgallery.transaction.ServiceFactory;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Services for Links Endpoint requests.
 *
 * @author Sidharta Noleto
 *
 */
public class TechnologyLinkServiceImpl implements TechnologyLinkService {

  /*
   * Constants --------------------------------------------
   */
  private static final Logger log = Logger.getLogger(TechnologyLinkServiceImpl.class.getName());

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyLinkServiceImpl instance;

  TechnologyLinkDAO technologyLinkDao = TechnologyLinkDAOImpl.getInstance();
  UserServiceTG userService = UserServiceTGImpl.getInstance();
  TechnologyService techService = TechnologyServiceImpl.getInstance();
  private TechnologyFollowersService followersService = ServiceFactory.createServiceImplementation(
      TechnologyFollowersService.class, TechnologyFollowersServiceImpl.class);

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyLinkServiceImpl() {}

  /**
   * Singleton method for the service.
   */
  public static TechnologyLinkServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyLinkServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public TechnologyLink addLink(TechnologyLink link, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    log.info("Starting creating Technology Link.");

    final Technology technology = link.getTechnology().get();

    validateUser(user);
    validateLink(link);
    validateTechnology(technology);

    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    final TechnologyLink newLink = addNewLink(link, techUser, technology);

    // TODO: Adicionar contador de links na tecnologia
    // techService.addLinksCounter(technology);
    techService.audit(technology.getId(), user);

    followTechnology(technology, techUser);

    return newLink;
  }

  private void followTechnology(final Technology technology, final TechGalleryUser techUser)
      throws BadRequestException {
    TechnologyFollowers technologyFollowers = followersService.findById(technology.getId());
    if (technologyFollowers == null
        || !technologyFollowers.getFollowers().contains(Ref.create(techUser))) {
      technologyFollowers = followersService.follow(technologyFollowers, techUser, technology);
      followersService.update(technologyFollowers);
      userService.updateUser(techUser);
    }
  }

  @Override
  public Response getLinksByTech(String techId, User user) throws InternalServerErrorException,
      BadRequestException, NotFoundException, OAuthRequestException {

    final Technology technology = techService.getTechnologyById(techId, user);

    validateUser(user);
    validateTechnology(technology);

    final List<TechnologyLink> linksByTech =
        technologyLinkDao.findAllByTechnology(technology);
    final TechnologyLinksTO response = new TechnologyLinksTO();
    response.setLinks(linksByTech);

    return response;
  }

  @Override
  public TechnologyLink deleteLink(Long linkId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException {

    validateDeletion(linkId, user);

    final TechnologyLink link = technologyLinkDao.findById(linkId);
    technologyLinkDao.delete(link);

    // TODO: Atualiza contador de links na tecnologia quando tivermos
    // techService.removeLinksCounter(link.getTechnology().get());

    return link;
  }

  private TechnologyLink addNewLink(TechnologyLink link, TechGalleryUser techUser,
      Technology technology) {
    log.info("Adding new Link...");

    final TechnologyLink newLink =
        new TechnologyLink(link.getDescription(), link.getLink(), technology, techUser, new Date());
    final Key<TechnologyLink> newLinkKey = technologyLinkDao.add(newLink);
    newLink.setId(newLinkKey.getId());

    log.info("New Link added: " + newLink.getId());

    return newLink;
  }

  /**
   */
  private void validateLink(TechnologyLink link) throws BadRequestException {

    log.info("Validating the link");

    if (link == null || link.getDescription() == null || link.getDescription().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.DESCRIPTION_CANNOT_BLANK.message());
    }

    if (link == null || link.getLink() == null || link.getLink().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.LINK_CANNOT_BLANK.message());
    }

    if (link.getDescription().length() > 100) {
      throw new BadRequestException(ValidationMessageEnums.DESCRIPTION_MUST_BE_LESSER.message());
    }

    if (link != null && link.getLink() != null && !link.getLink().isEmpty()) {
      Pattern p = Pattern
        .compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://|https://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");

      Matcher m = p.matcher(link.getLink());

      if( !m.matches() ) {
        throw new BadRequestException(ValidationMessageEnums.LINK_MUST_BE_VALID.message());
      }
    }
  }

  /**
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  private void validateLink(Long linkId) throws BadRequestException, NotFoundException {

    log.info("Validating the link");

    if (linkId == null) {
      throw new BadRequestException(ValidationMessageEnums.LINK_ID_CANNOT_BLANK.message());
    }

    final TechnologyLink link = technologyLinkDao.findById(linkId);
    if (link == null) {
      throw new NotFoundException(ValidationMessageEnums.LINK_NOT_EXIST.message());
    }
  }

  /**
   * @throws BadRequestException in case a request with problem were made.
   * @throws NotFoundException in case the information are not founded
   */
  private void validateTechnology(Technology technology)
      throws BadRequestException, NotFoundException {
    log.info("Validating the technology");
    if (technology == null) {
      throw new NotFoundException(ValidationMessageEnums.TECHNOLOGY_NOT_EXIST.message());
    }
  }

  /**
   * Validate the user logged in.
   *
   * @param user info about user from google
   *
   * @throws BadRequestException in case a request with problem were made.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   */
  private void validateUser(User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    log.info("Validating user to link");

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

  /**
   * @throws BadRequestException in case a request with problem were made.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   */
  private void validateDeletion(Long linkId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    log.info("Validating the deletion");

    validateLink(linkId);
    validateUser(user);

    final TechnologyLink link = technologyLinkDao.findById(linkId);
    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (!link.getAuthor().get().equals(techUser)) {
      throw new BadRequestException(ValidationMessageEnums.LINK_AUTHOR_ERROR.message());
    }
  }

  @Override
  public TechnologyLink getById(Long id) throws NotFoundException {
    final TechnologyLink link = technologyLinkDao.findById(id);
    if (link == null) {
      throw new NotFoundException(ValidationMessageEnums.LINK_NOT_EXIST.message());
    } else {
      return link;
    }
  }


}
