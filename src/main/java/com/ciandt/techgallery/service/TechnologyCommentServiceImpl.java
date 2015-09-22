package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.util.TechnologyCommentConverter;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Services for Comments Endpoint requests.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentServiceImpl implements TechnologyCommentService {

  private static final Logger log = Logger.getLogger(TechnologyCommentServiceImpl.class.getName());

  TechnologyCommentDAO technologyCommentDAO = new TechnologyCommentDAOImpl();
  TechGalleryUserDAO techGalleryUserDAO = new TechGalleryUserDAOImpl();
  TechnologyDAO technologyDAO = new TechnologyDAOImpl();

  @Override
  public Response addComment(TechnologyCommentTO comment, User user)
      throws InternalServerErrorException, BadRequestException {
    log.info("Starting creating Technology Comment.");

    validateInputs(comment, user);

    Technology technology = technologyDAO.findById(comment.getTechnologyId());
    TechGalleryUser techUser = techGalleryUserDAO.findByGoogleId(user.getUserId());

    TechnologyComment newComment = addNewComment(comment, techUser, technology);
    TechnologyCommentTO ret = TechnologyCommentConverter.fromEntityToTransient(newComment);

    return ret;
  }

  private TechnologyComment addNewComment(TechnologyCommentTO comment, TechGalleryUser techUser,
      Technology technology) {
    log.info("Adding new skill...");

    TechnologyComment newComment =
        new TechnologyComment(comment.getComment(), technology, techUser, new Date(), Boolean.TRUE);
    Key<TechnologyComment> newCommentKey = technologyCommentDAO.add(newComment);
    newComment.setId(newCommentKey.getId());

    log.info("New skill added: " + newComment.getId());

    return newComment;
  }

  /**
   * Validate inputs of TechnologyCommentTO.
   * 
   * @param comment inputs to be validate
   * @param user info about user from google
   * @throws BadRequestException .
   */
  private void validateInputs(TechnologyCommentTO comment, User user) throws BadRequestException {

    log.info("Validating inputs of comment");

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    TechGalleryUser techUser = techGalleryUserDAO.findByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new BadRequestException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }

    if (comment == null || comment.getComment() == null || comment.getComment().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_CANNOT_BLANK.message());
    }
    
    if (comment.getComment().length() > 500) {
      throw new BadRequestException(ValidationMessageEnums.COMMENT_MUST_BE_LESSER.message());
    }

    if (comment.getTechnologyId() == null || comment.getTechnologyId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BLANK.message());
    }

    Technology technology = technologyDAO.findById(comment.getTechnologyId());
    if (technology == null) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_NOT_EXIST.message());
    }
  }
}
