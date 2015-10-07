package com.ciandt.techgallery.service.util;

import com.google.api.server.spi.config.Transformer;
import com.google.api.server.spi.response.NotFoundException;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.impl.TechnologyServiceImpl;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * TechnologyCommentConverter methods.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentTransformer
    implements Transformer<TechnologyComment, TechnologyCommentTO> {

  private TechnologyService techService = TechnologyServiceImpl.getInstance();
  private TechGalleryUserTransformer tgUserTransformer = new TechGalleryUserTransformer();
  private TechnologyTransformer techTransformer = new TechnologyTransformer();

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  @Override
  public TechnologyCommentTO transformTo(TechnologyComment entity) {
    TechnologyCommentTO commentTO = new TechnologyCommentTO();
    TechGalleryUserTransformer userTransformer = new TechGalleryUserTransformer();
    commentTO.setId(entity.getId());
    commentTO.setComment(entity.getComment());
    commentTO.setTechnologyId(entity.getTechnology().get().getId());
    commentTO.setCreation(entity.getTimestamp());
    commentTO.setAuthor(userTransformer.transformTo(entity.getAuthor().get()));

    return commentTO;
  }

  /**
   * Transform a list of entity from datastore into list of response entity which is transient.
   * 
   * @param list entity from datastore
   * @return list transient entity
   */
  public List<TechnologyCommentTO> fromEntityToTransient(List<TechnologyComment> entities) {

    List<TechnologyCommentTO> commentsTO = new ArrayList<TechnologyCommentTO>();
    for (TechnologyComment entity : entities) {
      commentsTO.add(transformTo(entity));
    }

    return commentsTO;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param transient entity
   * @return entity from datastore
   * @throws NotFoundException
   */
  @Override
  public TechnologyComment transformFrom(TechnologyCommentTO tranzient) {
    TechnologyComment commentEntity = new TechnologyComment();
    commentEntity.setId(tranzient.getId());
    commentEntity.setComment(tranzient.getComment());
    try {
      commentEntity
          .setTechnology(Ref.create(techService.getTechnology(tranzient.getTechnologyId())));
    } catch (NotFoundException e) {
      commentEntity.setTechnology(null);
    }
    TechGalleryUser author = tgUserTransformer.transformFrom(tranzient.getAuthor());
    Ref<TechGalleryUser> ref = Ref.create(author);
    commentEntity.setAuthor(ref);
    return commentEntity;
  }
}
