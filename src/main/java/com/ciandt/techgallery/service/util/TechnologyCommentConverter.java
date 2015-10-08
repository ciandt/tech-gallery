package com.ciandt.techgallery.service.util;

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
public class TechnologyCommentConverter {

  private TechnologyService techService = TechnologyServiceImpl.getInstance();
  private TechGalleryUserTransformer tgUserTransformer = new TechGalleryUserTransformer();
  private TechnologyTransformer techTransformer = new TechnologyTransformer();

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  public static TechnologyCommentTO fromEntityToTransient(TechnologyComment entity) {
    TechnologyCommentTO commentTo = new TechnologyCommentTO();
    TechGalleryUserTransformer userTransformer = new TechGalleryUserTransformer();
    commentTo.setId(entity.getId());
    commentTo.setComment(entity.getComment());
    commentTo.setTechnologyId(entity.getTechnology().get().getId());
    commentTo.setCreation(entity.getTimestamp());
    commentTo.setAuthor(userTransformer.transformTo(entity.getAuthor().get()));

    return commentTo;
  }

  /**
   * Transform a list of entity from datastore into list of response entity which is transient.
   * 
   * @param entities entity from datastore
   * @return list transient entity
   */
  public static List<TechnologyCommentTO> fromEntityToTransient(List<TechnologyComment> entities) {

    List<TechnologyCommentTO> commentsTo = new ArrayList<TechnologyCommentTO>();
    for (TechnologyComment entity : entities) {
      commentsTo.add(fromEntityToTransient(entity));
    }

    return commentsTo;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param tranzient entity
   * @return entity from datastore
   * @throws NotFoundException in case the information are not founded
   */
  public TechnologyComment fromTransientToEntity(TechnologyCommentTO tranzient)
      throws NotFoundException {
    TechnologyComment commentEntity = new TechnologyComment();
    commentEntity.setId(tranzient.getId());
    commentEntity.setComment(tranzient.getComment());
    commentEntity.setTechnology(Ref.create(techTransformer.transformFrom(
        (TechnologyResponse) techService.getTechnology(tranzient.getTechnologyId()))));
    TechGalleryUser author = tgUserTransformer.transformFrom(tranzient.getAuthor());
    Ref<TechGalleryUser> ref = Ref.create(author);
    commentEntity.setAuthor(ref);
    return commentEntity;
  }
}
