package com.ciandt.techgallery.service.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyCommentDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.TechnologyCommentService;
import com.ciandt.techgallery.service.TechnologyRecommendationService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO; 
import com.ciandt.techgallery.service.model.TechnologyCommentsTO;
import com.ciandt.techgallery.service.util.TechnologyCommentConverter;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

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

  TechnologyCommentDAO technologyCommentDAO = TechnologyCommentDAOImpl.getInstance();

  UserServiceTG userService = UserServiceTGImpl.getInstance();
  TechnologyRecommendationService recommendationService = TechnologyRecommendationServiceImpl.getInstance();
  TechnologyService techService = TechnologyServiceImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyCommentServiceImpl() {
  }

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
  public Response addComment(TechnologyCommentTO comment, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException {
    log.info("Starting creating Technology Comment.");

    validateUser(user);
    validateComment(comment);
    validateTechnology(comment.getTechnologyId());

    Technology technology = techService.getTechnologyById(comment.getTechnologyId());
    TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());

    TechnologyComment newComment = addNewComment(comment, techUser, technology);
    techService.addCommentariesCounter(technology);
    TechnologyCommentTO ret = TechnologyCommentConverter.fromEntityToTransient(newComment);

    return ret;
  }

  @Override
  public Response getCommentsByTech(String techId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {

    validateUser(user);
    validateTechnology(techId);

    Technology technology = techService.getTechnologyById(techId);
    List<TechnologyComment> commentsByTech = technologyCommentDAO.findAllActivesByTechnology(technology);
    TechnologyCommentsTO response = new TechnologyCommentsTO();
    response.setComments(TechnologyCommentConverter.fromEntityToTransient(commentsByTech));
    for (TechnologyCommentTO commentTO : response.getComments()) {
      setCommentRecommendation(commentTO);
    }
    return response;
  }

  @Override
  public Response deleteComment(Long commentId, User user)
      throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {

    validateDeletion(commentId, user);

    TechnologyComment comment = technologyCommentDAO.findById(commentId);
    comment.setActive(false);
    technologyCommentDAO.update(comment);
    techService.removeCommentariesCounter(comment.getTechnology().get());
    TechnologyCommentTO response = TechnologyCommentConverter.fromEntityToTransient(comment);
    return response;
  }

  private TechnologyComment addNewComment(TechnologyCommentTO comment, TechGalleryUser techUser,
      Technology technology) {
    log.info("Adding new Comment...");

    TechnologyComment newComment = new TechnologyComment(comment.getComment(), technology, techUser, new Date(),
        Boolean.TRUE);
    Key<TechnologyComment> newCommentKey = technologyCommentDAO.add(newComment);
    newComment.setId(newCommentKey.getId());

    log.info("New Comment added: " + newComment.getId());

    return newComment;
  }

  /**
   * If the comment referenced by commentTO was created because of a
   * recommendation, sets the recommendation score
   * 
   * @param commentTO
   *          the comment
   */
  private void setCommentRecommendation(TechnologyCommentTO commentTO) {
    TechnologyComment comment = technologyCommentDAO.findById(commentTO.getId());
    TechnologyRecommendation techRecommendation;
    techRecommendation = recommendationService.getRecommendationByComment(comment);

    if (techRecommendation != null && techRecommendation.getActive() == true) {
      commentTO.setRecommendationId(techRecommendation.getId());
      commentTO.setRecommendationScore(techRecommendation.getScore());
    } else {
      commentTO.setRecommendationId(null);
      commentTO.setRecommendationScore(null);
    }
  }

  /**
   * Validate inputs of TechnologyCommentTO.
   * 
   * @param comment
   *          inputs to be validate
   * @throws BadRequestException
   *           .
   */
  private void validateComment(TechnologyCommentTO comment) throws BadRequestException {

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
   * @param comment
   *          id to be validate
   * @throws BadRequestException
   *           .
   */
  private void validateComment(Long commentId) throws BadRequestException {

    log.info("Validating the comment");

    if (commentId == null) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_ID_CANNOT_BLANK.message());
    }

    TechnologyComment comment = technologyCommentDAO.findById(commentId);
    if (comment == null) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_NOT_EXIST.message());
    }
  }

  /**
   * Validate technology.
   * 
   * @param id
   *          of technology
   * @throws NotFoundException
   * @throws BadRequestException
   *           .
   */
  private void validateTechnology(String idTechnology) throws BadRequestException, NotFoundException {

    log.info("Validating the technology");

    if (idTechnology == null || idTechnology.isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BLANK.message());
    }

    Technology technology = techService.getTechnologyById(idTechnology);
    if (technology == null) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NOT_EXIST.message());
    }
  }

  /**
   * Validate the user logged in.
   * 
   * @param user
   *          info about user from google
   * @throws InternalServerErrorException
   * @throws NotFoundException
   * @throws BadRequestException
   *           .
   */
  private void validateUser(User user) throws BadRequestException, NotFoundException, InternalServerErrorException {

    log.info("Validating user to comment");

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

  /**
   * Validate comment of TechnologyCommentTO.
   * 
   * @param comment
   *          inputs to be validate
   * @throws InternalServerErrorException
   * @throws NotFoundException
   * @throws BadRequestException
   *           .
   */
  private void validateDeletion(Long commentId, User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    log.info("Validating the deletion");

    validateComment(commentId);
    validateUser(user);

    TechnologyComment comment = technologyCommentDAO.findById(commentId);
    TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (!comment.getAuthor().get().equals(techUser)) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_AUTHOR_ERROR.message());
    }
  }

}
