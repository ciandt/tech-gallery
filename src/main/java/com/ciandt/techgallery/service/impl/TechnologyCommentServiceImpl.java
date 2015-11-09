package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyCommentDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;
import com.ciandt.techgallery.service.TechnologyCommentService;
import com.ciandt.techgallery.service.TechnologyFollowersService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentsTO;
import com.ciant.techgallery.transaction.ServiceFactory;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Services for Comments Endpoint requests.
 *
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentServiceImpl implements TechnologyCommentService {

  /*
   * Constants --------------------------------------------
   */
  private static final Logger log = Logger.getLogger(TechnologyCommentServiceImpl.class.getName());

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyCommentServiceImpl instance;

  TechnologyCommentDAO technologyCommentDao = TechnologyCommentDAOImpl.getInstance();
  UserServiceTG userService = UserServiceTGImpl.getInstance();
  TechnologyService techService = TechnologyServiceImpl.getInstance();
  private TechnologyFollowersService followersService = ServiceFactory.createServiceImplementation(
      TechnologyFollowersService.class, TechnologyFollowersServiceImpl.class);

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyCommentServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> Jo�o Felipe de Medeiros Moreira </a>
   * @since 07/10/2015
   *
   * @return TechnologyCommentServiceImpl instance.
   */
  public static TechnologyCommentServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyCommentServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public TechnologyComment addComment(TechnologyComment comment, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    log.info("Starting creating Technology Comment.");

    final Technology technology = comment.getTechnology().get();

    validateUser(user);
    validateComment(comment);
    validateTechnology(technology);

    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    final TechnologyComment newComment = addNewComment(comment, techUser, technology);
    techService.addCommentariesCounter(technology);
    techService.audit(technology.getId(), user);

    followTechnology(technology, techUser);

    UserProfileServiceImpl.getInstance().handleCommentChanges(newComment);
    return newComment;
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
  public Response getCommentsByTech(String techId, User user) throws InternalServerErrorException,
      BadRequestException, NotFoundException, OAuthRequestException {

    final Technology technology = techService.getTechnologyById(techId, user);

    validateUser(user);
    validateTechnology(technology);

    final List<TechnologyComment> commentsByTech =
        technologyCommentDao.findAllActivesByTechnology(technology);
    final TechnologyCommentsTO response = new TechnologyCommentsTO();
    response.setComments(commentsByTech);
    /*
     * for (TechnologyComment comment : response.getComments()) { setCommentRecommendation(comment);
     * }
     */
    return response;
  }

  @Override
  public TechnologyComment deleteComment(Long commentId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException,
      OAuthRequestException {

    validateDeletion(commentId, user);

    final TechnologyComment comment = technologyCommentDao.findById(commentId);
    comment.setActive(false);
    technologyCommentDao.update(comment);
    techService.removeCommentariesCounter(comment.getTechnology().get());

    UserProfileServiceImpl.getInstance().handleCommentChanges(comment);
    return comment;
  }

  private TechnologyComment addNewComment(TechnologyComment comment, TechGalleryUser techUser,
      Technology technology) {
    log.info("Adding new Comment...");

    final TechnologyComment newComment =
        new TechnologyComment(comment.getComment(), technology, techUser, new Date(), Boolean.TRUE);
    final Key<TechnologyComment> newCommentKey = technologyCommentDao.add(newComment);
    newComment.setId(newCommentKey.getId());

    log.info("New Comment added: " + newComment.getId());

    return newComment;
  }

  /**
   * Validate inputs of TechnologyCommentTO.
   *
   * @param comment inputs to be validate
   * @throws BadRequestException .
   */
  private void validateComment(TechnologyComment comment) throws BadRequestException {

    log.info("Validating the comment");

    if (comment == null || comment.getComment() == null || comment.getComment().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_CANNOT_BLANK.message());
    }

    if (comment.getComment().length() > 500) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_MUST_BE_LESSER.message());
    }
  }

  /**
   * Validate comment of TechnologyCommentTO.
   *
   * @param comment id to be validate
   *
   * @throws NotFoundException in case the information are not founded
   * @throws BadRequestException in case a request with problem were made.
   */
  private void validateComment(Long commentId) throws BadRequestException, NotFoundException {

    log.info("Validating the comment");

    if (commentId == null) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_ID_CANNOT_BLANK.message());
    }

    final TechnologyComment comment = technologyCommentDao.findById(commentId);
    if (comment == null) {
      throw new NotFoundException(ValidationMessageEnums.COMMENT_NOT_EXIST.message());
    }
  }

  /**
   * Validate technology.
   *
   * @param id of technology
   *
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

    log.info("Validating user to comment");

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

  /**
   * Validate comment of TechnologyCommentTO.
   *
   * @param comment inputs to be validate
   * @throws BadRequestException in case a request with problem were made.
   * @throws InternalServerErrorException in case something goes wrong
   * @throws NotFoundException in case the information are not founded
   */
  private void validateDeletion(Long commentId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    log.info("Validating the deletion");

    validateComment(commentId);
    validateUser(user);

    final TechnologyComment comment = technologyCommentDao.findById(commentId);
    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (!comment.getAuthor().get().equals(techUser)) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_AUTHOR_ERROR.message());
    }
  }

  @Override
  public TechnologyComment getById(Long id) throws NotFoundException {
    final TechnologyComment comment = technologyCommentDao.findById(id);
    if (comment == null) {
      throw new NotFoundException(ValidationMessageEnums.COMMENT_NOT_EXIST.message());
    } else {
      return comment;
    }
  }


}
